plugins {
    id("buildsrc.convention.kotlin-jvm")
    `java-library`
}

dependencies {
    api(libs.kotlinxSerializationCore)

    testImplementation(kotlin("test"))
}
