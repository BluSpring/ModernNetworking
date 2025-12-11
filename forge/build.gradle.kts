@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named

plugins {
    id("com.gradleup.shadow")
    kotlin("jvm")
    `maven-publish`
}

val loader = prop("loom.platform")!!
val minecraftVersion: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")?.project) {
    "No common project for $project"
}

version = "${mod.version}+$minecraftVersion"

base {
    archivesName.set("${mod.id}-$loader")
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    forge {
        mixinConfigs("modernnetworking.mixins.json")
    }
}

val commonBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shade: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
    get("developmentForge").extendsFrom(commonBundle)
}

repositories {
    maven("https://maven.minecraftforge.net")
}

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-${common.mod.dep("forge_loader")}")
    "io.github.llamalad7:mixinextras-forge:${mod.dep("mixin_extras")}".let {
        implementation(it)
        include(it)
    }

    compileOnly(project.project(":common:$minecraftVersion").sourceSets.main.get().output)
    shade(api(project(":api")) { isTransitive = false })
    modApi("dev.nyon:KotlinLangForge:${common.mod.dep("kotlinlangforge")}-k${common.mod.dep("kotlin")}-${common.mod.dep("kotlinlangforge_loader")}+forge")
}

tasks.processResources {
    from(project.project(":common:$minecraftVersion").sourceSets.main.get().resources)

    properties(listOf("META-INF/mods.toml", "pack.mcmeta"),
        "version" to mod.version,
        "minecraft" to common.mod.prop("mc_dep_forgelike"),
        "kotlinlangforge" to common.mod.dep("kotlinlangforge"),
        "kotlinlangforge_loader" to common.mod.dep("kotlinlangforge_loader")
    )
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(zipTree(project.project(":common:$minecraftVersion").tasks.jar.get().archiveFile))
}

tasks.sourcesJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(zipTree(project.project(":common:$minecraftVersion").tasks.sourcesJar.get().archiveFile))
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}

tasks.register<Copy>("buildAndCollect") {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader"))
    dependsOn("build")
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(shade)
    archiveClassifier = "dev-shadow"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(zipTree(project.project(":common:$minecraftVersion").tasks.jar.get().archiveFile))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "xyz.bluspring.modernnetworking"
            artifactId = "modernnetworking-forge"
            //version = project.version

            artifact(project.tasks.getByName("remapJar")) {
                builtBy(project.tasks.getByName("remapJar"))
            }
            artifact(project.tasks.getByName("remapSourcesJar")) {
                builtBy(project.tasks.getByName("remapSourcesJar"))
            }
        }
    }
}
