plugins {
    kotlin("jvm")
    `maven-publish`
}

base {
    archivesName.set("modernnetworking-api")
}

dependencies {
    compileOnly("io.netty:netty-buffer:4.1.97.Final")
}

java {
    withSourcesJar()
    withJavadocJar()

    val java = JavaVersion.VERSION_1_8
    targetCompatibility = java
    sourceCompatibility = java
}

kotlin {
    jvmToolchain(8)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "xyz.bluspring.modernnetworking"
            artifactId = "modernnetworking-api"
            //version = project.version

            from(components["java"])
        }
    }
}
