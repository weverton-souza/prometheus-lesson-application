package com.prometheus.lesson.resource.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale

typealias LocaleService = (code: String) -> String

@Service
class LocaleServiceImpl(
    private val messageSource: MessageSource
) : LocaleService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(LocaleServiceImpl::class.java)
    }

    override fun invoke(code: String): String {
        LOGGER.info("Fetching message for code: $code and request locale")
        return this.messageSource.getMessage(code, null, Locale.getDefault())
            .also {
                LOGGER.info("Fetched message: $it for code: $code")
            }
    }
}
