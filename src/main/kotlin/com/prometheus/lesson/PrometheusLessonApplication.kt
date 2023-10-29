package com.prometheus.lesson

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableReactiveMongoRepositories
class PrometheusLessonApplication

fun main(args: Array<String>) {
    runApplication<PrometheusLessonApplication>(*args)
}
