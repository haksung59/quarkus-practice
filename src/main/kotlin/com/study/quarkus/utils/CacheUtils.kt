package com.study.quarkus.utils

import io.quarkus.cache.CacheKeyGenerator
import io.quarkus.cache.CompositeCacheKey
import java.lang.reflect.Method
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CacheUtils: CacheKeyGenerator {

    @Inject
    lateinit var cache: String

    override fun generate(method: Method?, vararg methodParams: Any?): Any {
        return CompositeCacheKey(cache, methodParams[1])
    }
}