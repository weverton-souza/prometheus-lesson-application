package com.prometheus.lesson.service.impl

import com.prometheus.lesson.configuration.property.ChatGPTProperties
import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import com.prometheus.lesson.exception.ResourceNotFoundException
import com.prometheus.lesson.payload.question.converter.QuestionConverter
import com.prometheus.lesson.payload.question.request.CreateUpdateQuestionTO
import com.prometheus.lesson.payload.question.response.ChatGPTRequest
import com.prometheus.lesson.payload.question.response.QuestionResponseTO
import com.prometheus.lesson.repository.QuestionRepository
import com.prometheus.lesson.service.QuestionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class QuestionServiceImpl(
    private val questionRepository: QuestionRepository,
    @Qualifier("webClient4ChatGPT") private val webClient4ChatGPT: WebClient,
    private val chatGPTProperties: ChatGPTProperties
) : QuestionService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(QuestionServiceImpl::class.java)
    }

    override fun save(request: CreateUpdateQuestionTO, isNew: Boolean): Mono<QuestionResponseTO> {
        LOGGER.info("[save] Saving question: $request")
        return this.questionRepository.save(QuestionConverter.toQuestion(request, isNew))
            .map { QuestionConverter.toQuestionResponseTO(it) }
    }

    override fun findById(id: UUID): Mono<QuestionResponseTO> {
        LOGGER.info("[findById] Finding question by id: $id")
        return this.questionRepository.findById(id)
            .switchIfEmpty(Mono.error(ResourceNotFoundException("Question with id: $id not found")))
            .map {
                LOGGER.info("[findById] Found question by id: $id")
                QuestionConverter.toQuestionResponseTO(it)
            }
    }

    override fun findAll(
        search: String,
        pageable: Pageable,
        filter: FilterByType?,
        order: OrderByType?
    ): Mono<Page<QuestionResponseTO>> {
        LOGGER.info("[findAll] Finding all questions")
        return this.questionRepository.findAll(search, pageable, filter, order)
            .map { QuestionConverter.toQuestionResponseTO(it) }
            .collectList()
            .zipWith(this.questionRepository.count())
            .map { tuple -> PageImpl(tuple.t1, pageable, tuple.t2 / (pageable.pageNumber + 1)) }
            .map { page -> page }
    }

    override fun findAll(
        ids: MutableList<UUID>
    ): Flux<QuestionResponseTO> {
        LOGGER.info("[findAll] Finding all questions")
        return this.questionRepository.findAllById(ids)
            .map { QuestionConverter.toQuestionResponseTO(it) }
    }

    override fun deleteById(id: UUID): Mono<Void> {
        LOGGER.info("[deleteById] Deleting question by id: $id")
        return this.questionRepository.deleteById(id)
    }

    override fun getChatGPTResponse(request: ChatGPTRequest): Mono<Any> {
        return webClient4ChatGPT.post()
            .uri(chatGPTProperties.url)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Any::class.java)
    }
}
