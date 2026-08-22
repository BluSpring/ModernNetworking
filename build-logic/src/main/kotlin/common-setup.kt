import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.google.gson.JsonParser
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.NodeChildren
import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.ReleaseType
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import java.net.HttpURLConnection
import java.net.URI

fun Project.setupCommonUnmodded(module: String) {
    version = "${mod.version}"

    try {
        val stonecutter = project.extensions.getByType<StonecutterBuildExtension>()
        version = "${mod.version}+${stonecutter.current.version}"
    } catch (_: Throwable) {}

    project.extensions.configure<BasePluginExtension>("base") {
        archivesName.set("${mod.id}-$module")
    }

    project.extensions.configure<PublishingExtension>("publishing") {
        repositories {
            maven("https://mvn.devos.one/releases") {
                name = "devOS"
                credentials {
                    username = System.getenv()["MAVEN_USER"]
                    password = System.getenv()["MAVEN_PASS"]
                }
            }
        }

        publications {
            register<MavenPublication>("maven") {
                artifactId = "${mod.id}-$module"
                from(components.getByName("java"))
            }
        }
    }

    tasks.named("publishMavenPublicationToDevOSRepository") {
        onlyIf {
            val group = mod.group
            val artifactId = "${mod.id}-$module"
            val version = project.version.toString()

            try {
                val connection = URI.create("https://mvn.devos.one/releases/${group.replace(".", "/")}/${artifactId}/${version}/${artifactId}-${version}.jar").toURL().openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                println("https://mvn.devos.one/releases/${group.replace(".", "/")}/${artifactId}/${version}/${artifactId}-${version}.jar")

                connection.responseCode != 200
            } catch (_: Exception) {
                false
            }
        }
    }
}

fun Project.setupCommon(module: String) {
    setupCommonUnmodded(module)

    val stonecutter = project.extensions.getByType<StonecutterBuildExtension>()

    version = "${mod.version}+${stonecutter.current.version}"

    stonecutter.constants.match(module, "fabric", "forge", "neoforge",
        "common" // do not use lmao
    )

    val apiProj = rootProject.project(":api")
    if (module != "api") {
        dependencies {
            "api"(apiProj.extensions.getByName<SourceSetContainer>("sourceSets").named("main").get().output)
        }
    }

    if (module != "common") {
        val common = stonecutter.node.sibling("") ?: return
        val shadedDep by configurations.creating
        val commonProj = project.project(":common:${stonecutter.current.version}")

        dependencies {
            "api"(commonProj.extensions.getByName<SourceSetContainer>("sourceSets").named("main").get().output)
        }

        val properLoaderName = when (module) {
            "fabric" -> "Fabric"
            "forge" -> "Forge"
            "neoforge" -> "NeoForge"
            else -> ""
        }

        val base = project.extensions.getByType<BasePluginExtension>()
        base.archivesName.set("${common.project.mod.name}-${module}")

        // setup publishing to Modrinth and such
        apply(plugin = "me.modmuss50.mod-publish-plugin")
        project.extensions.configure<ModPublishExtension>("publishMods") {
            displayName = "${common.project.mod.version}+${stonecutter.current.version} ($properLoaderName)"
            version = "${common.project.mod.version}+${stonecutter.current.version}-$module"
            changelog = rootProject.file("CHANGELOG.md").readText()
            type = ReleaseType.STABLE
            modLoaders.add(module)

            dryRun = providers.environmentVariable("MODRINTH_TOKEN")
                .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

            modrinth {
                projectId = rootProject.property("publishing.modrinth").toString()
                accessToken = providers.environmentVariable("MODRINTH_TOKEN")

                minecraftVersions.addAll((property("supported_versions")!! as String).split(","))

                if (project.path.contains("fabric")) {
                    requires {
                        slug = "fabric-api"
                    }
                    requires {
                        slug = "fabric-language-kotlin"
                    }
                } else if (project.path.contains("forge")) {
                    requires {
                        slug = "kotlin-lang-forge"
                    }
                }
            }

            curseforge {
                projectId = rootProject.property("publishing.curseforge").toString()
                accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")

                minecraftVersions.addAll((property("supported_versions")!! as String).split(","))
                client = true
                server = true

                if (module == "fabric") {
                    requires {
                        slug = "fabric-api"
                    }
                    requires {
                        slug = "fabric-language-kotlin"
                    }
                } else if (module.contains("forge")) {
                    requires {
                        slug = "kotlinlangforge"
                    }
                }
            }
        }

        tasks.named("publish") {
            finalizedBy("publishMods")
        }

        tasks.named("publishModrinth") {
            onlyIf {
                try {
                    val connection = URI.create("https://api.modrinth.com/v2/project/modernnetworking/version/${version}").toURL().openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connect()

                    connection.responseCode != 200
                } catch (_: Exception) {
                    false
                }
            }
        }

        tasks.named<Jar>("jar") {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(zipTree(apiProj.tasks.named<Jar>("jar").get().archiveFile))
            from(zipTree(commonProj.tasks.named<Jar>("jar").get().archiveFile))

            if (module != "forge" && !(module == "common" && stonecutter.eval(stonecutter.current.version, "<=1.20.1"))) // ??????
                archiveClassifier = "dev"
        }

        tasks.named<Jar>("sourcesJar") {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(zipTree(apiProj.tasks.named<Jar>("sourcesJar").get().archiveFile))
            from(zipTree(commonProj.tasks.named<Jar>("sourcesJar").get().archiveFile))
        }

        tasks.named<ShadowJar>("shadowJar") {
            configurations = listOf(shadedDep)

            if (!shouldRemap() || module == "neoforge") {
                archiveClassifier = null
            }

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(zipTree(apiProj.tasks.named<Jar>("jar").get().archiveFile))
            from(zipTree(commonProj.tasks.named<Jar>("jar").get().archiveFile))
        }

        tasks.named<ProcessResources>("processResources") {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(apiProj.extensions.getByName<SourceSetContainer>("sourceSets").named("main").get().resources)
            from(commonProj.extensions.getByName<SourceSetContainer>("sourceSets").named("main").get().resources)
        }

        tasks.register<Copy>("buildAndCollect") {
            group = "versioned"
            description = "Must run through 'chiseledBuild'"

            if (shouldRemap() && module == "fabric")
                from(tasks.named<Jar>("remapJar").get().archiveFile)
            else if (module == "forge")
                from(tasks.named<Jar>("reobfJar").get().archiveFile)
            else
                from(tasks.named<Jar>("shadowJar").get().archiveFile)
            into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$module"))
            dependsOn("build")
        }
    }

    val commonProject = stonecutter.node.sibling("")?.project ?: this

    tasks.named<ProcessResources>("processResources") {
        properties(listOf("fabric.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml"),
            "mod_id" to commonProject.mod.id,
            "mod_name" to commonProject.mod.name,
            "mod_description" to commonProject.mod.description,
            "mod_version" to commonProject.mod.version,
            "mod_sources" to commonProject.mod.sources,

            "minecraft_version_range_fabric" to commonProject.property("minecraft_version_range_fabric")!!,
            "minecraft_version_range_forge" to commonProject.property("minecraft_version_range_forge")!!,
        )
    }
}

val cachedAvailableParchment = mutableMapOf<String, String?>()
val cachedNeoForm = mutableMapOf<String, String>()

fun tryFindParchmentSnapshot(version: String): String? {
    if (cachedAvailableParchment.contains(version))
        return cachedAvailableParchment[version]

    return try {
        val metadata = URI("https://maven.parchmentmc.org/org/parchmentmc/data/parchment-$version/maven-metadata.xml").toURL().openStream()
            .use { XmlSlurper().parse(it) }

        (metadata.getProperty("versioning") as NodeChildren).getProperty("release").toString()
    } catch (_: Throwable) {
        null
    }.apply {
        cachedAvailableParchment[version] = this
    }
}

fun tryFindNeoFormVersion(version: String): String? {
    if (cachedNeoForm.contains(version))
        return cachedNeoForm[version]

    return try {
        val json = URI("https://maven.neoforged.net/api/maven/versions/releases/net/neoforged/neoform?sorted=true").toURL()
            .run { JsonParser.parseString(this.readText()) }.asJsonObject

        val versions = json.getAsJsonArray("versions").toList().reversed().map { it.asString }
        for (neoFormVersion in versions) {
            if (neoFormVersion.startsWith("$version-")) {
                return neoFormVersion.apply {
                    cachedNeoForm[version] = this
                }
            }
        }

        println("Could not find NeoForm version!")
        null
    } catch (e: Throwable) {
        println("An error occurred whilst searching for NeoForm version!")
        e.printStackTrace()
        null
    }
}

fun Project.shouldRemap(): Boolean {
    val stonecutter = this.extensions.getByType<StonecutterBuildExtension>()
    return stonecutter.eval(stonecutter.current.version, "<=1.21.11")
}

val Project.minimumJavaVersion: Int
    get() {
        val stonecutter = this.extensions.getByType<StonecutterBuildExtension>()
        return if (stonecutter.eval(stonecutter.current.version, ">1.21.11"))
            25
        else if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
            21
        else if (stonecutter.eval(stonecutter.current.version, ">=1.18"))
            17
        else if (stonecutter.eval(stonecutter.current.version, ">=1.17")) // jail :(
            16
        else
            8
    }

fun Project.shadedDep(notation: Any): Dependency? {
    return this.dependencies.add("shadedDep", notation)
}
