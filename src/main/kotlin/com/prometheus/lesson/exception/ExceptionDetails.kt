package com.prometheus.lesson.exception

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@Schema(description = "Custom error response for DesignHub errors", hidden = true)
interface ExceptionDetails {
    @get:Schema(description = "HTTP status of the error response", example = "BAD_REQUEST")
    val status: HttpStatus

    @get:Schema(description = "Error details", example = "An error occurred.")
    val details: String

    @get:Schema(description = "Error message for developers", example = "InternalServerError")
    val developerHelper: DeveloperHelper?

    @get:Schema(description = "Date and time when the error occurred", example = "2023-07-16T10:49:47.150939")
    val timestamp: String
}

data class DeveloperHelper(
    val exceptionClass: String,
    val cause: String?,
    val originClass: String?,
    val originLine: Int?
)
