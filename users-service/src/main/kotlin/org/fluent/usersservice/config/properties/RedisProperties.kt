package org.fluent.usersservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("redis")
class RedisProperties {
    lateinit var host: String
    var port: Int = 6379
    lateinit var password: String
    var database: Int = 0
}