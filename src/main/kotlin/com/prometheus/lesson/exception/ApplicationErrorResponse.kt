package com.prometheus.lesson.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

data class ApplicationErrorResponse(
    override val status: HttpStatus,
    override val details: String,
    override val developerHelper: DeveloperHelper?,
    override val timestamp: String,
    @JsonInclude(Include.NON_NULL, content = Include.NON_EMPTY)
    @ArraySchema(schema = Schema(implementation = MethodArgumentNotValidDetails::class))
    val fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
) : ExceptionDetails {

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    open class Builder {
        companion object {
            val LOGGER: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
        }

        private var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        private var details: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        private var developerHelper: DeveloperHelper? = null
        private var timestamp: String = LocalDateTime.now().toString()
        private var fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null

        fun status(status: HttpStatus): Builder {
            this.status = status
            return this
        }

        fun details(details: String?): Builder {
            if (details != null) {
                this.details = details
            }
            return this
        }

        fun developerHelper(developerHelper: DeveloperHelper?): Builder {
            this.developerHelper = developerHelper
            return this
        }

        fun fieldErrors(fieldErrors: MutableCollection<MethodArgumentNotValidDetails>): Builder {
            this.fieldErrors = fieldErrors
            return this
        }

        fun build(): ResponseEntity<ExceptionDetails> {
            return this.buildExceptionMessage(
                this.status,
                this.details,
                this.developerHelper,
                this.timestamp,
                this.fieldErrors
            )
        }

        private fun buildExceptionMessage(
            status: HttpStatus,
            details: String,
            developerHelper: DeveloperHelper? = null,
            timestamp: String,
            fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
        ): ResponseEntity<ExceptionDetails> {
            return try {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Message Error: 
                        status: $status, 
                        details: $details, developerHelper: $developerHelper, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )

                ResponseEntity(
                    ApplicationErrorResponse(status, details, developerHelper, timestamp, fieldErrors),
                    status
                )
            } catch (ex: Exception) {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Message not found: 
                        status: $status, 
                        details: $details, developerMessage: $developerHelper, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )
                ResponseEntity(
                    ApplicationErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        details,
                        developerHelper,
                        timestamp,
                        fieldErrors
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
        }
    }
}
