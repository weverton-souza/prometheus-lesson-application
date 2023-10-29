package com.prometheus.lesson.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
class ResourceNotFoundException(msg: String) : RuntimeException(msg)
