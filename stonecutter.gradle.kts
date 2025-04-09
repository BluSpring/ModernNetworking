import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("dev.kikugie.stonecutter")
    kotlin("jvm") version "2.1.20" apply false
    id("dev.architectury.loom") version "1.9-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "8.3.5" apply false
}

stonecutter active "1.18.2" /* [SC] DO NOT EDIT */
stonecutter.automaticPlatformConstants = true

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.parchmentmc.org")
    }

    // KFF doesn't have newer versions of Kotlin in older versions of Forge, so.
    if ((project.name.contains("forge") || project.name.contains("neoforge")) && project.tasks.findByName("shadowJar") != null) {
        apply(plugin = "com.gradleup.shadow")

        tasks {
            getByName<ShadowJar>("shadowJar") {
                relocate("org.jetbrains", "xyz.bluspring.modernnetworking.shaded.jetbrains")
                relocate("kotlin", "xyz.bluspring.modernnetworking.shaded.kotlin")
                relocate("kotlinx", "xyz.bluspring.modernnetworking.shaded.kotlinx")
            }
        }
    }
}

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

// Builds loader-specific versions into `build/libs/{mod.version}/{loader}`
for (it in stonecutter.tree.branches) {
    if (it.id.isEmpty()) continue
    val loader = it.id.upperCaseFirst()
    stonecutter registerChiseled tasks.register("chiseledBuild$loader", stonecutter.chiseled) {
        group = "project"
        versions { branch, _ -> branch == it.id }
        ofTask("buildAndCollect")
    }
}

// Runs active versions for each loader
for (it in stonecutter.tree.nodes) {
    if (it.metadata != stonecutter.current || it.branch.id.isEmpty()) continue
    val types = listOf("Client", "Server")
    val loader = it.branch.id.upperCaseFirst()
    for (type in types) it.tasks.register("runActive$type$loader") {
        group = "project"
        dependsOn("run$type")
    }
}
