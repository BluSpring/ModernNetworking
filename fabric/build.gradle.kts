import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    alias(libs.plugins.fletching.table)
    alias(libs.plugins.shadow)
    `maven-publish`
}

if (shouldRemap()) {
    apply(plugin = "net.fabricmc.fabric-loom-remap")
} else {
    apply(plugin = "net.fabricmc.fabric-loom")
}

setupCommon("fabric")
setupCommonLoom("fabric")

val loom = extensions.getByType<LoomGradleExtensionAPI>()

val shadedDep = configurations.named("shadedDep").get()

sourceSets {
    create("testmod") {
        compileClasspath += main.get().compileClasspath
        compileClasspath += main.get().output

        runtimeClasspath += main.get().runtimeClasspath
        runtimeClasspath += main.get().output
    }
}

loom.runs {
    register("testmodClient") {
        client()
        displayName = "Testmod Client"

        sourceSet = "testmod"
        runDirectory = file("run/test")
    }

    register("testmodServer") {
        server()
        displayName = "Testmod Server"

        sourceSet = "testmod"
        runDirectory = file("run/test/server")
    }
}

dependencies {
    moddedImplementation(libs.fabric.loader)
    moddedApi(libs.fabric.kotlin)
    api(libs.mixinextras.fabric)
    annotationProcessor(libs.mixinextras.fabric)

    moddedApi("net.fabricmc.fabric-api:fabric-api:${property("fabric_api")}")
}
