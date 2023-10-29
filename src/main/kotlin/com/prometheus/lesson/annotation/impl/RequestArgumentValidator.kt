package com.prometheus.lesson.annotation.impl

import com.prometheus.lesson.annotation.RequestArgument
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class RequestArgumentValidator : ConstraintValidator<RequestArgument, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return !value.isNullOrEmpty()
    }
}
