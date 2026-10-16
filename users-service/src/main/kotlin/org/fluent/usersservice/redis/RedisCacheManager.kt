package org.fluent.usersservice.redis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory

@Configuration(proxyBeanMethods = false)
class RedisCacheManager(
    @Qualifier("users-cache")
    private val userCacheConfiguration: RedisCacheConfiguration
) {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        return RedisCacheManager.builder(connectionFactory)
            .withInitialCacheConfigurations(
                mapOf(
                    "users:v1:by-id" to userCacheConfiguration,
                    "users:v1:by-email" to userCacheConfiguration,
                )
            )
            .build()
    }
}
