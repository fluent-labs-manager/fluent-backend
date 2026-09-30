package org.fluent.api

import org.fluent.core.CoreModule
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    fun health(): Map<String, String> = mapOf(
        "status" to "ok",
        "module" to CoreModule::class.simpleName.orEmpty()
    )
}
