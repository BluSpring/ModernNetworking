plugins {
    id("architectury-plugin")
    id("dev.architectury.loom")
    kotlin("jvm")
    id("dev.kikugie.j52j") version "1.0"
}

val minecraftVersion = stonecutter.current.version

version = "${mod.version}+$minecraftVersion"
base {
    archivesName.set("${mod.id}-common")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.prop("loom.platform")
})

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.layered() {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$minecraftVersion:${mod.prop("parchment_snapshot")}")
    })

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