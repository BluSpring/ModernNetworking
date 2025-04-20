plugins {
    id("dev.kikugie.j52j") version "1.0"
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