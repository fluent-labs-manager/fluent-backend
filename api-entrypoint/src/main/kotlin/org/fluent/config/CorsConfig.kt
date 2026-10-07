package org.fluent.config

import org.fluent.config.properties.HttpServicesProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(private val serviceProperties: HttpServicesProperties) {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration().apply {
            addAllowedOrigin(serviceProperties.frontendProduction)
            addAllowedOriginPattern(serviceProperties.frontendProductionPattern)

            allowedMethods = listOf("*")
            allowedHeaders = listOf(
                "Content-Type",
                "Authorization",
                "Accept",
                "Origin",
                "X-Requested-With",
                "User-Email",
                "sentry-trace",
                "baggage",
            )
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }
}
