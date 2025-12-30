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

plugins {
    id("dev.kikugie.stonecutter") version("0.7.+")
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true

    val versions = listOf("1.18.2", "1.19.2", "1.20.4", "1.20.6", "1.21.1", "1.21.7", "1.21.10", "1.21.11")
    // Guess what?
    // In 1.19 and later, Forge actually changes the fucking names, which Fabric doesn't do.
    // So, this is what we have to deal with.
    val exclusiveForgeVersions = listOf("1.19.2")
    val versionsWithoutNeoForge = listOf("1.18.2")

    // Oh boy, NeoForge does a networking split between client and server! Now we need to
    // handle that too!
    val exclusiveNeoForgeVersions = listOf("1.21.7")

    create(rootProject) {
        versions(versions)
        vcsVersion = "1.20.4"

        branch("common")
        branch("fabric") {
            versions(versions.filter { !exclusiveForgeVersions.contains(it) && !exclusiveNeoForgeVersions.contains(it) })
        }
        branch("forge") {
            versions(versions.filter { !exclusiveNeoForgeVersions.contains(it) }.filterIndexed { i, _ -> i <= versions.indexOf("1.20.4") })
        }
        branch("neoforge") {
            versions(versions.filter { !exclusiveForgeVersions.contains(it) && !versionsWithoutNeoForge.contains(it) })
        }
    }
}

include("api")
include("bukkit")
include("velocity")

rootProject.name = "ModernNetworking"