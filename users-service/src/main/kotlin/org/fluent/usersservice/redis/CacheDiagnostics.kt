package org.fluent.usersservice.redis

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class CacheDiagnostics(
    private val cacheManager: org.springframework.cache.CacheManager,
) : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments) {
        logger.debug("CacheManager: {}", cacheManager.javaClass.name)

        cacheManager.cacheNames.forEach { name ->
            val cache = cacheManager.getCache(name)

            logger.debug(
                "Cache {}: wrapper={}, native={}",
                name,
                cache?.javaClass?.name,
                cache?.nativeCache?.javaClass?.name,
            )
        }
    }
}