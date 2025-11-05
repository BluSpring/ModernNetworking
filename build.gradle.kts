plugins {
    kotlin("jvm") version "2.2.20" apply false
    id("com.gradleup.shadow") version "8.3.5" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.7.+" apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.parchmentmc.org")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
    }

    group = "xyz.bluspring.modernnetworking"
    version = mod.version
}
