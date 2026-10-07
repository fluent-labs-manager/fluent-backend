package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", libs.requiredLibrary("springBootStarterSecurity"))
    add("implementation", libs.requiredLibrary("jjwt"))
    add("implementation", libs.requiredLibrary("jaxbApi"))
}
