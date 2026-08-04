pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
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

val versions = listOf("1.18.2", "1.20.4", /*"1.20.6",*/ "1.21.1", "1.21.4", "1.21.8", "26.1.2", "26.2")

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true

    create(rootProject) {
        versions(versions)
        vcsVersion = "1.18.2"

        branch("common")
        branch("fabric")
        branch("neoforge") {
            // NeoForge doesn't exist for <=1.20.1
            versions(versions.filter { stonecutter.eval(it, ">1.20.1") })
        }
        branch("forge") {
            // Forge is hard to test for 1.20.2+, the codebase just will not load, don't bother
            versions(versions.filter { stonecutter.eval(it, "<=1.20.1") })
        }
    }
}

include("api")
include("bukkit")

includeBuild("build-logic")

rootProject.name = "ModernNetworking"
