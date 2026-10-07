package buildsrc.convention

import org.gradle.api.InvalidUserDataException
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

/**
 * Resolves a required library without leaking [java.util.Optional] into
 * precompiled Kotlin DSL scripts, whose IDE classpath is handled separately.
 */
fun VersionCatalog.requiredLibrary(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow {
        InvalidUserDataException("Library alias '$alias' is missing from the '$name' version catalog")
    }
