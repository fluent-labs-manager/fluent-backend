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
    add("implementation", platform(libs.findLibrary("springBootDependencies").get()))
    add("implementation", libs.findLibrary("springBootStarterWeb").get())
    add("implementation", libs.findLibrary("springBootStarterValidation").get())
    add("implementation", libs.findLibrary("kotlinReflect").get())
    add("implementation", libs.findLibrary("kotlinxCoroutines").get())
    add("implementation", libs.findLibrary("kotlinxCoroutinesReactor").get())
    add("implementation", libs.findLibrary("kotlinxSerialization").get())
    add("implementation", libs.findLibrary("reactorKotlinExtensions").get())

    add("developmentOnly", libs.findLibrary("springBootDevtools").get())
    add("testImplementation", libs.findLibrary("springBootStarterTest").get())
}
