package com.prometheus.lesson.utils

import org.springframework.core.MethodParameter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import kotlin.math.min

/**
 * A custom implementation of [HandlerMethodArgumentResolver] for resolving [Pageable] arguments.
 * This class is responsible for extracting pagination and sorting parameters from the incoming
 * HTTP request and creating a [Pageable] object.
 */
class PageableHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {

    /**
     * Determines if this resolver supports the given method parameter.
     *
     * @param parameter The method parameter.
     * @return A Boolean indicating if the parameter is supported.
     */
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return Pageable::class.java == parameter.parameterType
    }

    /**
     * Resolves the [Pageable] argument from the incoming HTTP request.
     *
     * @param methodParameter The method parameter.
     * @param bindingContext The current binding context.
     * @param serverWebExchange The server web exchange.
     * @return A [Mono] containing the resolved [Pageable] object.
     */
    override fun resolveArgument(
        methodParameter: MethodParameter,
        bindingContext: BindingContext,
        serverWebExchange: ServerWebExchange
    ): Mono<Any> {
        val pageValues = serverWebExchange.request.queryParams.getOrDefault("page", listOf(DEFAULT_PAGE))
        val sizeValues = serverWebExchange.request.queryParams.getOrDefault("size", listOf(DEFAULT_SIZE))
        val page = pageValues[0]
        val sortParam = serverWebExchange.request.queryParams.getFirst("sort")
        var sort: Sort? = Sort.unsorted()
        if (sortParam != null) {
            val parts = sortParam.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts.size == 2) {
                val property = parts[0]
                val direction = Sort.Direction.fromString(parts[1])
                sort = Sort.by(direction, property)
            }
        }
        return Mono.just(
            PageRequest.of(
                page.toInt(),
                min(sizeValues[0].toInt(), MAX_SIZE),
                sort!!
            )
        )
    }

    companion object {
        private const val DEFAULT_PAGE = "0"
        private const val DEFAULT_SIZE = "10"
        private const val MAX_SIZE = 50
    }
}
