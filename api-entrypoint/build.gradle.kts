plugins {
    id("buildsrc.convention.kotlin-jvm")
    alias(libs.plugins.kotlinPluginSpring)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(project(":core"))

    implementation(platform(libs.springBootDependencies))
    implementation(libs.springBootStarterWeb)

    testImplementation(libs.springBootStarterTest)
}

tasks.bootJar {
    archiveFileName = "api-entrypoint.jar"
}
