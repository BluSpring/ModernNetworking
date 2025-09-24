@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.named


plugins {
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
    fabric()
}

val commonBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shade: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
    get("developmentFabric").extendsFrom(commonBundle)
}

repositories {
    maven("https://maven.terraformersmc.com")
    maven("https://maven.nucleoid.xyz/") // Not sure why but we need this
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${common.mod.dep("fabric_loader")}")
    modApi("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")
    modApi("net.fabricmc:fabric-language-kotlin:${common.mod.dep("fabric_language_kotlin")}")

    compileOnly(project.project(":common:$minecraftVersion").sourceSets.main.get().output)

    shade(api(project(":api")) { isTransitive = false })
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(zipTree(project.project(":common:$minecraftVersion").tasks.jar.get().archiveFile))
}

tasks.sourcesJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(zipTree(project.project(":common:$minecraftVersion").tasks.sourcesJar.get().archiveFile))
}

tasks.processResources {
    properties(listOf("fabric.mod.json"),
        "version" to mod.version,
        "minecraft" to common.mod.prop("mc_dep_fabric"),
        "loader_version" to common.mod.dep("fabric_loader"),
        "kotlin_loader_version" to common.mod.dep("fabric_language_kotlin")
    )
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
    from(zipTree(project.project(":common:$minecraftVersion").tasks.remapJar.get().archiveFile))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "xyz.bluspring.modernnetworking"
            artifactId = "modernnetworking-fabric"
            //version = project.version

            artifact(project.tasks.getByName("remapJar")) {
                builtBy(project.tasks.getByName("remapJar"))
            }
            artifact(project.tasks.getByName("remapSourcesJar")) {
                builtBy(project.tasks.getByName("remapSourcesJar"))
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
