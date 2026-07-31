plugins {
    alias(libs.plugins.kotlin)
    `maven-publish`
    id("multiplatform")
}

setupCommonUnmodded("api")

dependencies {
    compileOnly(libs.netty.buffer)
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(8)
}
