pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
        gradlePluginPortal()
    }
}

// can't use libs.versions.toml for this - https://github.com/gradle/gradle/issues/36437
// make sure to update it there too tho.
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" // https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
    id("dev.kikugie.stonecutter") version "0.9.+" // https://stonecutter.kikugie.dev/
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true

    val versions = listOf("1.20.4", "1.20.6", "1.21.1", "1.21.8", "1.21.10", "1.21.11", "26.1")

    create(rootProject) {
        versions(versions)
        vcsVersion = "1.20.4"

        branch("common")
        branch("fabric")
        branch("neoforge") {
            // NeoForge doesn't exist for <=1.20.1
            versions(versions.filter { stonecutter.eval(it, ">1.20.1") })
        }
        /*branch("forge") {
            // KLF doesn't exist for >=1.20.5, don't bother
            // MDG also does not work with versions older than 1.20.1 either, from the looks.
            versions(versions.filter { stonecutter.eval(it, "<=1.20.1") })
        }*/
    }
}

include("api")
include("bukkit")
include("velocity")

rootProject.name = "ModernNetworking"