import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.ReleaseType
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    id("dev.kikugie.stonecutter")
    kotlin("jvm") apply false
    id("dev.architectury.loom") version "1.10-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
    id("dev.kikugie.j52j") version "2.0" apply false
}

stonecutter active "1.20.4" /* [SC] DO NOT EDIT */

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.parchmentmc.org")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
    }

    group = "xyz.bluspring.modernnetworking"
    version = mod.version
}

subprojects {
    if (name == "api")
        return@subprojects

    if (project.extensions.findByName("stonecutter") == null)
        return@subprojects

    if (parent == rootProject)
        return@subprojects

    val sc = project.extensions.getByType<StonecutterBuildExtension>()
    val common = sc.node.sibling("")

    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "com.gradleup.shadow")

    val minecraftVersion = sc.current.version
    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    loom.silentMojangMappingsLicense()

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        "mappings"(loom.layered() {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-$minecraftVersion:${common?.project?.mod?.prop("parchment_snapshot") ?: mod.prop("parchment_snapshot")}")
        })
    }

    loom.decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    loom.runConfigs.all {
        isIdeConfigGenerated = true
        runDir = "../../../run"
        vmArgs("-Dmixin.debug.export=true", "-XX:+AllowEnhancedClassRedefinition")
    }

    project.extensions.configure<JavaPluginExtension>("java") {
        withSourcesJar()
        val java = if (sc.eval(minecraftVersion, ">=1.20.5"))
            JavaVersion.VERSION_21 else JavaVersion.VERSION_17
        targetCompatibility = java
        sourceCompatibility = java
    }

    project.extensions.configure<KotlinProjectExtension>("kotlin") {
        jvmToolchain(if (sc.eval(minecraftVersion, ">=1.20.5")) 21 else 17)
    }

    tasks.named<Jar>("jar") {
        archiveClassifier = "dev"
    }

    tasks.named<RemapJarTask>("remapJar") {
        injectAccessWidener = true
        input = tasks.named<Jar>("shadowJar").get().archiveFile
        archiveClassifier = null
        dependsOn(tasks.named<Jar>("shadowJar"))
    }

    // KFF doesn't have newer versions of Kotlin in older versions of Forge, so.
    if ((project.name.contains("forge") || project.name.contains("neoforge")) && project.tasks.findByName("shadowJar") != null) {
        tasks {
            getByName<ShadowJar>("shadowJar") {
                relocate("org.jetbrains", "xyz.bluspring.modernnetworking.shaded.jetbrains")
                relocate("kotlin", "xyz.bluspring.modernnetworking.shaded.kotlin")
                relocate("kotlinx", "xyz.bluspring.modernnetworking.shaded.kotlinx")
                relocate("org.intellij", "xyz.bluspring.modernnetworking.shaded.intellij")
            }
        }
    }

    val properLoaderName = when (project.prop("loom.platform")) {
        "fabric" -> "Fabric"
        "forge" -> "Forge"
        "neoforge" -> "NeoForge"
        else -> ""
    }

    if (project.path.contains("fabric") || project.path.contains("forge")) {
        apply(plugin = "me.modmuss50.mod-publish-plugin")

        val common = sc.node.sibling("") ?: return@subprojects

        project.extensions.configure<ModPublishExtension>("publishMods") {
            file = tasks.named<RemapJarTask>("remapJar").get().archiveFile
            displayName = "${project.property("mod.version")}+${minecraftVersion} ($properLoaderName)"
            version = "${project.property("mod.version")}+${minecraftVersion}-${project.prop("loom.platform")}"
            changelog = rootProject.file("CHANGELOG.md").readText()
            type = ReleaseType.STABLE
            modLoaders.add(project.property("loom.platform").toString())

            dryRun = providers.environmentVariable("MODRINTH_TOKEN")
                .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

            modrinth {
                projectId = rootProject.property("publishing.modrinth").toString()
                accessToken = providers.environmentVariable("MODRINTH_TOKEN")

                if (common.project.mod.prop("supported_versions_forge") != "[VERSIONED]" && project.property("loom.platform") == "forge")
                    minecraftVersions.addAll(common.project.mod.prop("supported_versions_forge").split(","))
                else
                    minecraftVersions.addAll(common.project.mod.prop("supported_versions").split(","))
                if (project.path.contains("fabric")) {
                    requires {
                        slug = "fabric-api"
                    }
                    requires {
                        slug = "fabric-language-kotlin"
                    }
                } else if (project.path.contains("forge")) {
                    requires {
                        slug = "kotlin-for-forge"
                    }
                }
            }

            curseforge {
                projectId = rootProject.property("publishing.curseforge").toString()
                accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
                if (common.project.mod.prop("supported_versions_forge") != "[VERSIONED]" && project.property("loom.platform") == "forge")
                    minecraftVersions.addAll(common.project.mod.prop("supported_versions_forge").split(","))
                else
                    minecraftVersions.addAll(common.project.mod.prop("supported_versions").split(","))
                if (project.path.contains("fabric")) {
                    requires {
                        slug = "fabric-api"
                    }
                    requires {
                        slug = "fabric-language-kotlin"
                    }
                } else if (project.path.contains("forge")) {
                    requires {
                        slug = "kotlin-for-forge"
                    }
                }
            }
        }
    }
}

// Runs active versions for each loader
for (it in stonecutter.tree.nodes) {
    if (it.metadata != stonecutter.current || it.branch.id.isEmpty()) continue
    val types = listOf("Client", "Server")
    val loader = it.branch.id.upperCaseFirst()
    for (type in types) it.project.tasks.register("runActive$type$loader") {
        group = "project"
        dependsOn("run$type")
    }
}
