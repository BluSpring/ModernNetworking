import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.ReleaseType
import org.gradle.kotlin.dsl.named

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

version = mod.version

base {
    archivesName.set("${mod.id}-bukkit")
}

val shade: Configuration by configurations.creating

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    shade(api(project(":api")) { isTransitive = false })
    shade("org.jetbrains.kotlin:kotlin-stdlib")

    api("io.netty:netty-buffer:4.1.97.Final")
    api("io.netty:netty-codec:4.1.97.Final")
}

tasks {
    processResources {
        properties(listOf("plugin.yml"),
            "version" to mod.version
        )
    }

    jar {
        archiveClassifier = "dev"
    }

    getByName<ShadowJar>("shadowJar") {
        archiveClassifier = null

        relocate("org.jetbrains", "xyz.bluspring.modernnetworking.shaded.jetbrains")
        relocate("kotlin", "xyz.bluspring.modernnetworking.shaded.kotlin")
        relocate("kotlinx", "xyz.bluspring.modernnetworking.shaded.kotlinx")
    }
}

publishMods {
    file = tasks.named<ShadowJar>("shadowJar").get().archiveFile
    displayName = "${project.property("mod.version")} (Bukkit/PaperMC)"
    version = "${project.property("mod.version")}-bukkit"
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = ReleaseType.STABLE
    modLoaders.addAll(listOf("bukkit", "paper", "spigot", "purpur", "folia"))

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
            artifactId = "modernnetworking-bukkit"
            //version = project.version

            artifact(project.tasks.getByName("shadowJar")) {
                builtBy(project.tasks.getByName("shadowJar"))
            }
            artifact(project.tasks.getByName("kotlinSourcesJar")) {
                builtBy(project.tasks.getByName("kotlinSourcesJar"))
            }
        }
    }

    repositories {
        maven("https://mvn.devos.one/releases") {
            credentials {
                username = System.getenv()["MAVEN_USER"]
                password = System.getenv()["MAVEN_PASS"]
            }
        }
    }
}
