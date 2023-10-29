package com.prometheus.lesson.payload.question.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatGPTRequest(
    @JsonProperty("model")
    val model: String,

    @JsonProperty("messages")
    val messages: MutableList<Message>,

    @JsonProperty("temperature")
    val temperature: Double,

    @JsonProperty("max_tokens")
    val maxTokens: Int,

    @JsonProperty("top_p")
    val topP: Int,

    @JsonProperty("frequency_penalty")
    val frequencyPenalty: Int,

    @JsonProperty("presence_penalty")
    val presencePenalty: Int
)

data class Message(
    @JsonProperty("role")
    val role: String,

    @JsonProperty("content")
    val content: String
)
