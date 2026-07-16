plugins {
    alias(libs.plugins.kotlin)
    `maven-publish`
    id("multiplatform")
}

setupCommonUnmodded("api")

dependencies {
    compileOnly(libs.netty.buffer)
}

kotlin {
    jvmToolchain(8)
}
