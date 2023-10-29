package com.prometheus.lesson.utils

object I18n {
    const val HTTP_4XX_400_BAD_REQUEST = "http.4xx.400-bad-request"
    const val HTTP_4XX_401_UNAUTHORIZED = "http.4xx.401-unauthorized"
    const val HTTP_4XX_403_FORBIDDEN = "http.4xx.403-forbidden"
    const val HTTP_4XX_404_NOT_FOUND = "http.4xx.404-not-found"
    const val HTTP_4XX_409_CONFLICT = "http.4xx.409-conflict"
    const val HTTP_5XX_500_INTERNAL_SERVER_ERROR = "http.5xx.500-internal-server-error"

    const val REFRESH_TOKEN_NOT_FOUND_MESSAGE = "http.4xx.404-refresh-token-not-found.message"

    const val DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_DETAILS = "designhub.validation.constraints.NotBlank.details"
    const val DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_ENUM_DETAILS = "designhub.validation.constraints.NotBlank.enum.message"
}
