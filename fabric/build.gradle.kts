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

val shadedDep by configurations.named("shadedDep")

dependencies {
    val commonProject = stonecutter.node.sibling("")?.project ?: project

    moddedImplementation(libs.fabric.loader)
    moddedApi("net.fabricmc.fabric-api:fabric-api:${commonProject.mod.dep("fabric_api")}")
    moddedApi(libs.fabric.kotlin)
    api(libs.mixinextras.fabric)
    annotationProcessor(libs.mixinextras.fabric)

    shadedDep(api(project(":api")) {
        isTransitive = false
    })
}
