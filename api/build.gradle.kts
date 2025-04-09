plugins {
    kotlin("jvm")
}

dependencies {
    implementation("io.netty:netty-buffer:4.1.97.Final")
}

java {
    withSourcesJar()
    val java = JavaVersion.VERSION_1_8
    targetCompatibility = java
    sourceCompatibility = java
}

kotlin {
    jvmToolchain(8)
}