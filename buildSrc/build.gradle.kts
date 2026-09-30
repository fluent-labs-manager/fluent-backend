plugins {
    // The Kotlin DSL plugin provides a convenient way to develop convention plugins.
    // Convention plugins are located in `src/main/kotlin`, with the file extension `.gradle.kts`,
    // and are applied in the project's `build.gradle.kts` files as required.
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Add a dependency on the Kotlin Gradle plugin, so that convention plugins can apply it.
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.kotlinAllopenGradlePlugin)
    implementation(libs.kotlinNoargGradlePlugin)
    implementation(libs.kotlinSerializationGradlePlugin)
    implementation(libs.springBootGradlePlugin)
    implementation(libs.flywayGradlePlugin)
    // Makes the external plugin available to the precompiled convention plugin.
    implementation(libs.conventionalCommitsGradlePlugin)
}
