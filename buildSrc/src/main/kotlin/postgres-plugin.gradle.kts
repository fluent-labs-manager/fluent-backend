package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("org.flywaydb.flyway")
    kotlin("plugin.jpa")
}

dependencies {
    add("implementation", libs.requiredLibrary("springBootStarterDataJpa"))
    add("implementation", libs.requiredLibrary("flywayCore"))
    add("implementation", libs.requiredLibrary("flywayDatabasePostgresql"))
    add("implementation", libs.requiredLibrary("uuidCreator"))
    add("runtimeOnly", libs.requiredLibrary("postgresql"))
}
