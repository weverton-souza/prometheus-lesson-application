package com.prometheus.lesson.annotation.impl

import com.prometheus.lesson.annotation.ValueOfEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, String> {
    private val acceptedValues: MutableList<String> = mutableListOf()

    override fun initialize(constraintAnnotation: ValueOfEnum) {
        super.initialize(constraintAnnotation)
        acceptedValues.addAll(constraintAnnotation.enumClass.java.enumConstants.map { it.name })
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return if (value != null) {
            if (acceptedValues.contains(value)) {
                true
            } else {
                throw IllegalArgumentException("errors")
            }
        } else {
            false
        }
    }
}
