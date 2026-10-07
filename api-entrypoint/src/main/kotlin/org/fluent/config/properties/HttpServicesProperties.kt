package org.fluent.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("services")
class HttpServicesProperties {
    lateinit var frontendProduction: String
    lateinit var frontendProductionPattern: String
    lateinit var usersServiceURI: String
}