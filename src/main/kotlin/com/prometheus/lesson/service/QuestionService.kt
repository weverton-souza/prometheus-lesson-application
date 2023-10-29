package com.prometheus.lesson.service

import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import com.prometheus.lesson.payload.question.request.CreateUpdateQuestionTO
import com.prometheus.lesson.payload.question.response.ChatGPTRequest
import com.prometheus.lesson.payload.question.response.QuestionResponseTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface QuestionService {

    fun save(request: CreateUpdateQuestionTO, isNew: Boolean = true): Mono<QuestionResponseTO>

    fun findById(id: UUID): Mono<QuestionResponseTO>

    fun findAll(
        search: String = "",
        pageable: Pageable,
        filter: FilterByType? = null,
        order: OrderByType? = null
    ): Mono<Page<QuestionResponseTO>>

    fun findAll(
        ids: MutableList<UUID>
    ): Flux<QuestionResponseTO>

    fun deleteById(id: UUID): Mono<Void>

    fun getChatGPTResponse(request: ChatGPTRequest): Mono<Any>
}
