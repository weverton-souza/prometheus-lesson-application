package com.prometheus.lesson.configuration

import com.prometheus.lesson.utils.PageableHandlerMethodArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

/**
 * WebConfig is a configuration class that customizes WebFlux behavior.
 * It extends [WebFluxConfigurer] to customize argument resolution for controllers.
 */
@Configuration
class WebConfig : WebFluxConfigurer {

    /**
     * Configures custom argument resolvers for controller methods.
     * This method adds a custom [PageableHandlerMethodArgumentResolver] to handle Pageable arguments.
     *
     * @param configurer The [ArgumentResolverConfigurer] object to which custom resolvers can be added.
     */
    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(PageableHandlerMethodArgumentResolver())
    }
}
