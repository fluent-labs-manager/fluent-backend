plugins {
    id("buildsrc.convention.spring-app")
    id("buildsrc.convention.postgres-plugin")
    id("buildsrc.convention.security-plugin")
    id("buildsrc.convention.mapstruct")
}

dependencies {
    implementation(project(":core"))
    implementation(platform(libs.springCloudDependencies))
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springCloudStarterGateway)
    testImplementation(libs.reactorTest)
}

tasks.bootJar {
    archiveFileName = "api-entrypoint.jar"
}
