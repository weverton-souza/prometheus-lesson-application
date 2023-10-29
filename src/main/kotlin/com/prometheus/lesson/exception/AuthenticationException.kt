package com.prometheus.lesson.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
class AuthenticationException(msg: String) : RuntimeException(msg)
