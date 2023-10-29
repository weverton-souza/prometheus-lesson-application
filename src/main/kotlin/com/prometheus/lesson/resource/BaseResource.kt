package com.prometheus.lesson.resource

import com.prometheus.lesson.exception.ApplicationErrorResponse
import com.prometheus.lesson.exception.ExceptionDetails
import io.micrometer.observation.annotation.Observed
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@Observed
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "405",
            description = "Validation exception",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        implementation = ExceptionDetails::class
                    )
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid ID supplied",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid field(s) supplied",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApplicationErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        )
    ]
)
interface BaseResource
