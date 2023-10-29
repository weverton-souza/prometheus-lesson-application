package com.prometheus.lesson.exception

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Field validation error details", hidden = true)
data class MethodArgumentNotValidDetails(

    @Schema(description = "Name of the field with the error", example = "email")
    val field: String,

    @Schema(description = "Field error message", example = "The email field is required and cannot be empty")
    val message: String
)
