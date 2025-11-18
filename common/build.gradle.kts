plugins {
    id("architectury-plugin")
    id("dev.architectury.loom")
    id("dev.kikugie.fletching-table")
    `maven-publish`
}

val minecraftVersion = stonecutter.current.version
version = "${mod.version}+$minecraftVersion"

base {
    archivesName.set("${mod.id}-common")
}

architectury {
    common(stonecutter.tree.branches.mapNotNull {
        if (stonecutter.current.project !in it) null
        else it.project.prop("loom.platform")
    })
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
}

tasks.shadowJar {
    configurations = listOf()
}
