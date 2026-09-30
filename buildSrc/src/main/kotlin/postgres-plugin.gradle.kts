package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("org.flywaydb.flyway")
    kotlin("plugin.jpa")
}

dependencies {
    add("implementation", libs.findLibrary("springBootStarterDataJpa").get())
    add("implementation", libs.findLibrary("flywayCore").get())
    add("implementation", libs.findLibrary("flywayDatabasePostgresql").get())
    add("runtimeOnly", libs.findLibrary("postgresql").get())
}
