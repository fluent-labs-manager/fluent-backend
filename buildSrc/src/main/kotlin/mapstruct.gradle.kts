package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    kotlin("kapt")
}

dependencies {
    add("implementation", libs.requiredLibrary("mapstruct"))
    add("kapt", libs.requiredLibrary("mapstructProcessor"))
}
