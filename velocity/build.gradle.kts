import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.ReleaseType
import org.gradle.kotlin.dsl.named

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

version = mod.version

base {
    archivesName.set("${mod.id}-velocity")
}

val shade: Configuration by configurations.creating

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly(kapt("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")!!)

    shade(api(project(":api")) { isTransitive = false })
    shade("org.jetbrains.kotlin:kotlin-stdlib")

    api("io.netty:netty-buffer:4.1.97.Final")
    api("io.netty:netty-codec:4.1.97.Final")
}

val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")

tasks {
    val generateTemplates = create("generateTemplates", Copy::class) {
        val props = mapOf(
            "version" to mod.version
        )

        inputs.properties(props)
        from(templateSource)
        into(templateDest)
        expand(props)
    }

    sourceSets.main.get().java.srcDir(generateTemplates.outputs)

    processResources {
        properties(listOf("plugin.yml"),
            "version" to mod.version
        )
    }

    jar {
        archiveClassifier = "dev"
    }

    getByName<ShadowJar>("shadowJar") {
        configurations = listOf(shade)
        archiveClassifier = null
    }
}

publishMods {
    file = tasks.named<ShadowJar>("shadowJar").get().archiveFile
    displayName = "${project.property("mod.version")} (Velocity)"
    version = "${project.property("mod.version")}-velocity"
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = ReleaseType.STABLE
    modLoaders.addAll(listOf("velocity"))

    dryRun = providers.environmentVariable("MODRINTH_TOKEN")
        .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

    val supportedVersions = listOf(
        "1.16", "1.16.1", "1.16.2", "1.16.3", "1.16.4", "1.16.5",
        "1.17", "1.17.1",
        "1.18", "1.18.1", "1.18.2",
        "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4",
        "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6",
        "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5"
    )

    modrinth {
        projectId = rootProject.property("publishing.modrinth").toString()
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.addAll(supportedVersions)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "xyz.bluspring.modernnetworking"
            artifactId = "modernnetworking-velocity"
            //version = project.version

            artifact(project.tasks.getByName("shadowJar")) {
                builtBy(project.tasks.getByName("shadowJar"))
            }
            artifact(project.tasks.getByName("kotlinSourcesJar")) {
                builtBy(project.tasks.getByName("kotlinSourcesJar"))
            }
        }
    }
}
