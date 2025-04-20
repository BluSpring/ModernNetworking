plugins {
    id("dev.kikugie.j52j") version "1.0"
    `maven-publish`
}

val minecraftVersion = stonecutter.current.version
version = "${mod.version}+$minecraftVersion"

base {
    archivesName.set("${mod.id}-common")
}

architectury {
    common("fabric", "forge", "neoforge")
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    api(project(":api")) {
        isTransitive = false
    }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}

tasks.shadowJar {
    configurations = listOf()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "xyz.bluspring.modernnetworking"
            artifactId = "modernnetworking-common"
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
