plugins {
    id("buildsrc.convention.kotlin-jvm")
    kotlin("plugin.serialization")
    `java-library`
}

dependencies {
    api(libs.kotlinxSerializationCore)

    testImplementation(kotlin("test"))
}
