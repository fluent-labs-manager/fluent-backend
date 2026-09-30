package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    kotlin("kapt")
}

dependencies {
    add("implementation", libs.findLibrary("mapstruct").get())
    add("kapt", libs.findLibrary("mapstructProcessor").get())
}
