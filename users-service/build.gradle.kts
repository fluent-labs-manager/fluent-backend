plugins {
    id("buildsrc.convention.spring-app")
    id("buildsrc.convention.postgres-plugin")
    id("buildsrc.convention.security-plugin")
    id("buildsrc.convention.mapstruct")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.springBootStarterWeb)
}

tasks.bootJar {
    archiveFileName = "users-service.jar"
}
