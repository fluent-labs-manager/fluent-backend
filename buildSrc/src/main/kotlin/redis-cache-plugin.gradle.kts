package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", libs.requiredLibrary("springBootStarterDataRedis"))
    add("implementation", libs.requiredLibrary("springBootStarterCache"))
}
