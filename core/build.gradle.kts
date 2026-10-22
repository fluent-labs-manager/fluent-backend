plugins {
    id("buildsrc.convention.kotlin-jvm")
    kotlin("plugin.serialization")
    `java-library`
    `maven-publish`
}

group = "org.fluent.libs"
version = file("VERSION").readText().trim()

publishing {
    publications {
        create<MavenPublication>("fluentCoreComponents") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/fluent-labs-manager/fluent-backend")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    api(libs.kotlinxSerializationCore)

    testImplementation(kotlin("test"))
}
