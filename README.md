# fluent-backend

This project uses [Gradle](https://gradle.org/). To build and run the application, use the *Gradle* tool window by
clicking the Gradle icon in the right-hand toolbar, or run it directly from the terminal:

* Run `./gradlew run` to build and run the application.
* Run `./gradlew build` to only build the application.
* Run `./gradlew check` to run all checks, including tests.
* Run `./gradlew clean` to clean all build outputs.

Note the usage of the Gradle Wrapper (`./gradlew`). This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project is a Gradle multi-module build. The initial `app` and `utils` modules are retained from the generated
template. Backend code is being introduced in:

* `core` — shared Kotlin code: domain models, interfaces, errors and utilities with no HTTP or Spring application
  boundary.
* `api-entrypoint` — the first Spring Boot service. It declares `implementation(project(":core"))`, so it can use
  public code from `core` and Gradle builds `core` first when necessary.

Run the service with `./gradlew :api-entrypoint:bootRun`; then `GET http://localhost:8080/health` returns its health
status. Build every module with `./gradlew build`.

External dependency versions live in `gradle/libs.versions.toml`. Project dependencies belong in the consuming
module's `dependencies` block; use `implementation` by default. `core` also applies `java-library`, which enables
`api(...)` only for a dependency whose types are deliberately part of `core`'s public API. The shared build logic was
extracted to a convention plugin located in `buildSrc`.

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies and both a
build cache and a configuration cache (see `gradle.properties`).

## Publishing and using `core`

The `core` module is configured for publication to the GitHub Packages Maven registry for
`fluent-labs-manager/fluent-backend`. Its version comes from `core/VERSION`. To publish a release, update that file,
merge the change into `main`, then run the **Publish core to GitHub Packages** workflow from the Actions tab on `main`.
The workflow uses its repository `GITHUB_TOKEN` with `packages: write` permission.

For local publishing, put your GitHub username and a classic personal access token with `write:packages` permission in
your personal `~/.gradle/gradle.properties` (never in this repository):

```properties
gpr.user=YOUR_GITHUB_USERNAME
gpr.key=YOUR_PERSONAL_ACCESS_TOKEN
```

Then run `./gradlew :core:publish`. GitHub Packages also requires authentication when another project downloads the
library. In that project's `settings.gradle.kts`, add the repository:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/fluent-labs-manager/fluent-backend")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull
                password = providers.gradleProperty("gpr.key").orNull
            }
        }
    }
}
```

The consumer needs a classic personal access token with `read:packages` permission and access to the package. Add the
same `gpr.user` and `gpr.key` properties to its personal Gradle properties, then declare the dependency in the
consuming module:

```kotlin
dependencies {
    implementation("org.fluent.libs:core:0.1.0")
}
```

Replace `0.1.0` with the released value from `core/VERSION`.
