package org.fluent.config

import org.fluent.config.properties.HttpServicesProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig(private val servicesProperties: HttpServicesProperties) {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("users-service") { r ->
                r.path("/users-service/**")
                    .uri(servicesProperties.usersServiceURI)
            }
            .build()
    }
}