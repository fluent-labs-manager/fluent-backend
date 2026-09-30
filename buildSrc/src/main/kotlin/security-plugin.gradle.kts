package buildsrc.convention

import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", libs.findLibrary("springBootStarterSecurity").get())
    add("implementation", libs.findLibrary("jjwt").get())
    add("implementation", libs.findLibrary("jaxbApi").get())
}
