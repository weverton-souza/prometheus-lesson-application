package com.prometheus.lesson.configuration.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.lesson-core.chat-gpt")
class ChatGPTProperties(
    val url: String = "",
    val key: String = ""
)
