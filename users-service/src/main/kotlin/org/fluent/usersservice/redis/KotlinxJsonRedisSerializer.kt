package org.fluent.usersservice.redis

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.springframework.data.redis.serializer.RedisSerializer

class KotlinxJsonRedisSerializer<T>(
    private val serializer: KSerializer<T>,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
    },
) : RedisSerializer<T> {

    override fun serialize(value: T?): ByteArray? =
        value?.let {
            json.encodeToString(serializer, it).encodeToByteArray()
        }

    override fun deserialize(bytes: ByteArray?): T? =
        bytes?.let {
            json.decodeFromString(serializer, it.decodeToString())
        }
}