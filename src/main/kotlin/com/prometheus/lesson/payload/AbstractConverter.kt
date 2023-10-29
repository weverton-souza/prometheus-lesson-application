package com.prometheus.lesson.payload

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies

abstract class AbstractConverter {
    companion object {
        val modelMapper = ModelMapper().apply {
            configuration.matchingStrategy = MatchingStrategies.STRICT
        }
    }
}
