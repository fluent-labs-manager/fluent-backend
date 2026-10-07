package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("org.springframework.boot")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    val springBootBom = platform(libs.requiredLibrary("springBootDependencies"))

    add("implementation", springBootBom)
    add("developmentOnly", springBootBom)
    add("implementation", libs.requiredLibrary("springBootStarterValidation"))
    add("implementation", libs.requiredLibrary("kotlinReflect"))
    add("implementation", libs.requiredLibrary("kotlinxCoroutines"))
    add("implementation", libs.requiredLibrary("kotlinxCoroutinesReactor"))
    add("implementation", libs.requiredLibrary("kotlinxSerialization"))
    add("implementation", libs.requiredLibrary("reactorKotlinExtensions"))
    add("implementation", libs.requiredLibrary("dotenvSpringBoot"))

    add("developmentOnly", libs.requiredLibrary("springBootDevtools"))
    add("testImplementation", libs.requiredLibrary("springBootStarterTest"))
}

configureSpringAppTestConvention()
