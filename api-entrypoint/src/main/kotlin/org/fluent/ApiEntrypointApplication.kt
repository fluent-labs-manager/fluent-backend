package org.fluent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiEntrypointApplication

fun main(args: Array<String>) {
    runApplication<ApiEntrypointApplication>(*args)
}
