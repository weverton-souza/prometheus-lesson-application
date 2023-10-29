package com.prometheus.lesson.exception

import com.prometheus.lesson.resource.impl.LocaleService
import com.prometheus.lesson.utils.I18n.DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_DETAILS
import com.prometheus.lesson.utils.I18n.HTTP_4XX_400_BAD_REQUEST
import com.prometheus.lesson.utils.I18n.HTTP_4XX_401_UNAUTHORIZED
import com.prometheus.lesson.utils.I18n.HTTP_5XX_500_INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponseException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class ExceptionControllerAdvice(
    private val localeService: LocaleService
) {

    @ExceptionHandler(Exception::class, RuntimeException::class)
    fun handleGlobalException(
        ex: Exception,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        return ApplicationErrorResponse.builder()
            .details(this.localeService.invoke(HTTP_5XX_500_INTERNAL_SERVER_ERROR))
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .developerHelper(this.developerHelper(ex))
            .build()
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleGlobalException(
        ex: IllegalArgumentException,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        return ApplicationErrorResponse.builder()
            .details(this.localeService.invoke(HTTP_4XX_400_BAD_REQUEST))
            .status(HttpStatus.BAD_REQUEST)
            .developerHelper(this.developerHelper(ex))
            .build()
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(
        ex: ResourceNotFoundException,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        return ApplicationErrorResponse.builder()
            .details(this.localeService.invoke(ex.message!!))
            .developerHelper(this.developerHelper(ex))
            .status(HttpStatus.NOT_FOUND)
            .build()
    }

    @ExceptionHandler(ErrorResponseException::class)
    fun handleGlobalException(
        ex: ErrorResponseException,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        return ApplicationErrorResponse.builder()
            .details(this.localeService.invoke(HTTP_4XX_400_BAD_REQUEST))
            .status(HttpStatus.valueOf(ex.statusCode.value()))
            .developerHelper(this.developerHelper(ex))
            .build()
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        return ApplicationErrorResponse.builder()
            .details(this.localeService.invoke(HTTP_4XX_401_UNAUTHORIZED))
            .status(HttpStatus.UNAUTHORIZED)
            .developerHelper(this.developerHelper(ex))
            .build()
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handlerMethodArgumentNotValidException(
        ex: WebExchangeBindException,
        exchange: ServerWebExchange
    ): ResponseEntity<ExceptionDetails> {
        val fieldErrors2 = ArrayList<MethodArgumentNotValidDetails>()
        val errors = mutableListOf<String>()
        val fieldErrors = ex.bindingResult.fieldErrors

        for (fieldError in fieldErrors) {
            var errorMessage: String = fieldError.defaultMessage ?: ""
            errors.add(errorMessage)

            try {
                errorMessage = this.localeService.invoke(errorMessage)
                fieldErrors2.add(MethodArgumentNotValidDetails(fieldError.field, errorMessage))
            } catch (ex: Exception) {
                fieldErrors2.add(MethodArgumentNotValidDetails(fieldError.field, errorMessage))
            }
        }

        return ApplicationErrorResponse.builder()
            .fieldErrors(fieldErrors2)
            .details(this.localeService.invoke(DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_DETAILS))
            .developerHelper(this.developerHelper(ex))
            .status(HttpStatus.BAD_REQUEST)
            .build()
    }

    private fun developerHelper(ex: Exception) =
        DeveloperHelper(
            ex.javaClass.name,
            ex.cause?.message ?: ex.message ?: "",
            ex.stackTrace[0].className,
            ex.stackTrace[0].lineNumber
        )
}
