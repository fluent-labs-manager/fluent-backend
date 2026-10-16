package org.fluent.usersservice.redis

import org.fluent.core.dto.io.UserDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    @Qualifier("users-cache")
    fun userCacheConfiguration(): RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair
                .fromSerializer(StringRedisSerializer())
        )
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair
                .fromSerializer(KotlinxJsonRedisSerializer(UserDto.serializer()))
        )
        .disableCachingNullValues()
        .entryTtl(Duration.ofMinutes(10))

}