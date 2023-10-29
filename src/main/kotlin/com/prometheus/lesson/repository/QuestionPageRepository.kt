package com.prometheus.lesson.repository

import com.prometheus.lesson.domain.question.Question
import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * PageRepository is an interface for providing paginated CRUD operations on MongoDB documents.
 */
interface QuestionPageRepository {

    /**
     * Fetches all Question documents from MongoDB, according to the specified [Pageable] object.
     *
     * @param pageable The [Pageable] object containing pagination and sorting information.
     * @return A [Flux] that will emit the fetched [Question] documents.
     */
    fun findAll(search: String, pageable: Pageable, filter: FilterByType?, order: OrderByType?): Flux<Question>

    /**
     * Counts all Question documents in MongoDB, according to the specified [Pageable] object.
     *
     * @param pageable The [Pageable] object containing pagination and sorting information.
     * @return A [Mono] that will emit the count of [Question] documents.
     */
    fun countAllWithPageable(pageable: Pageable): Mono<Long>

    fun searchOnContentAndStatementsAllByExpression(
        exp: String,
        pageable: Pageable,
        filter: FilterByType?
    ): Flux<Question>
}
