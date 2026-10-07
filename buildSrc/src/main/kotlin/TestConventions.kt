package buildsrc.convention

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

fun Project.configureKotlinJvmConvention() {
    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            events(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
            )
        }
    }
}

fun Project.configureSpringAppTestConvention() {
    val excludedTestTags = providers.gradleProperty("excludeTestTags").orNull

    tasks.withType<Test>().configureEach {
        useJUnitPlatform {
            excludedTestTags
                ?.split(",")
                ?.map(String::trim)
                ?.filter(String::isNotBlank)
                ?.let { excludeTags(*it.toTypedArray()) }
        }

        jvmArgs("-XX:+EnableDynamicAgentLoading")

        filter {
            isFailOnNoMatchingTests = false
        }
    }
}
