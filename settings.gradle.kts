pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/releases")
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version("0.5")
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true

    create(rootProject) {
        versions("1.18.2", "1.20.4", "1.20.6", "1.21.1")
        vcsVersion = "1.20.4"

        branch("common")
        branch("fabric")
        branch("forge")
        branch("neoforge") {
            versions("1.20.4", "1.20.6", "1.21.1")
        }
    }
}

include("api")
include("bukkit")

rootProject.name = "ModernNetworking"