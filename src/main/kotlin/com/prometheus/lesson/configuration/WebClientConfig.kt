package com.prometheus.lesson.configuration

import com.prometheus.lesson.configuration.property.ChatGPTProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val chatGPTProperties: ChatGPTProperties
) {
    @Bean("webClient4ChatGPT")
    fun webClient(): WebClient {
        val client = HttpClient.create().responseTimeout(Duration.ofSeconds(45))

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(client))
            .baseUrl(chatGPTProperties.url)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", "Bearer ${chatGPTProperties.key}")
            .build()
    }
}
