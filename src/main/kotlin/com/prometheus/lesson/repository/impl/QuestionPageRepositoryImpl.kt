package com.prometheus.lesson.repository.impl

import com.prometheus.lesson.domain.question.Question
import com.prometheus.lesson.domain.question.QuestionContent
import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import com.prometheus.lesson.enumeration.OriginType
import com.prometheus.lesson.repository.QuestionPageRepository
import com.prometheus.lesson.service.impl.QuestionServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class QuestionPageRepositoryImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : QuestionPageRepository {

    companion object {
        private const val SMALL_WORD_BETWEEN_TAGS_REGEX = "<\\/b>([\\sa-zA-Z0-9\\p{IsLatin}]*)<b>"
        private const val WHITESPACE_BETWEEN_TAGS_REGEX = "<\\/b>(\\s+)<b>"

        val LOGGER: Logger = LoggerFactory.getLogger(QuestionServiceImpl::class.java)
    }

    override fun findAll(
        search: String,
        pageable: Pageable,
        filter: FilterByType?,
        order: OrderByType?
    ): Flux<Question> {
        return if (search.isEmpty()) {
            LOGGER.info("Fetching all questions with pagination.")
            this.reactiveMongoTemplate.find(buildQueryWithFilters(pageable, filter, order), Question::class.java)
        } else {
            LOGGER.info("Searching for questions with expression: {} and pagination.", search)
            this.searchOnContentAndStatementsAllByExpression(search, pageable, filter)
        }
    }

    override fun countAllWithPageable(pageable: Pageable): Mono<Long> {
        return reactiveMongoTemplate.count(buildQueryWithFilters(pageable), Question::class.java)
    }

    private fun buildQueryWithFilters(
        pageable: Pageable,
        filter: FilterByType? = null,
        order: OrderByType? = null
    ): Query {
        val query = Query()

        if (filter != null) {
            query.addCriteria(Criteria.where("origin").`is`(filter))
        }

        if (order != null) {
            val sortCriteria = if (filter != null && OriginType.getNationalExam().contains(filter.toString())) {
                Sort.by(Sort.Direction.fromString(order.toString()), "year")
            } else {
                Sort.by(Sort.Direction.fromString(order.toString()), "createdAt")
            }
            query.with(sortCriteria)
        }

        return query.with(pageable)
    }

    override fun searchOnContentAndStatementsAllByExpression(
        exp: String,
        pageable: Pageable,
        filter: FilterByType?
    ): Flux<Question> {
        val expWithNoSpecialCharacter = exp.replace("([^\\sa-zA-Z0-9\\p{IsLatin}])".toRegex(), "")
        val searchTerm = ".*${
        expWithNoSpecialCharacter.trim()
            .split(" ").filter { it.length > 3 }
            .joinToString("|")
        }.*"

        val orCriteria = buildOrCriteria(searchTerm, filter)

        return reactiveMongoTemplate.find(Query(orCriteria).with(pageable), Question::class.java)
            .map { question -> processQuestion(expWithNoSpecialCharacter, question) }
            .collectList()
            .map { list -> sortByExpressionCount(list) }
            .flatMapMany { Flux.fromIterable(it) }
    }

    private fun buildOrCriteria(searchTerm: String, filter: FilterByType?): Criteria {
        if (filter != null) {
            return Criteria().andOperator(
                Criteria.where("origin").`is`(filter),
                Criteria().orOperator(
                    Criteria.where("content.content").regex(searchTerm, "i"),
                    Criteria.where("content.statements").`is`(Regex(searchTerm, RegexOption.IGNORE_CASE))
                )
            )
        }
        return Criteria().orOperator(
            Criteria.where("content.content").regex(searchTerm, "i"),
            Criteria.where("content.statements").`is`(Regex(searchTerm, RegexOption.IGNORE_CASE))
        )
    }

    private fun processQuestion(exp: String, question: Question): Pair<Question, Long> {
        val content = question.content
        LOGGER.debug("Processing question content for question with ID: {}", question.id)
        val highlightedContent = highlightContent(exp, content)
        val updatedHighlightedContent = updateHighlightedContent(highlightedContent)
        val contentCount = countContent(exp, content)
        val statementsCount = countStatements(exp, content)

        return Pair(question.copy(content = updatedHighlightedContent), (contentCount + statementsCount.toLong()))
    }

    private fun highlightContent(exp: String, content: List<QuestionContent>): List<QuestionContent> {
        return content.map { contentItem ->
            when (contentItem) {
                is QuestionContent.Text -> {
                    var highlightedText: String = contentItem.content
                    exp.split(" ").forEach { term ->
                        if (term.length > 3) {
                            highlightedText = highlightedText
                                .replace(
                                    "(?i)$term(\\S)|(?i)$term".toRegex(),
                                    "<b>$0</b>"
                                )
                        }
                    }
                    contentItem.copy(content = highlightedText)
                }

                is QuestionContent.Statement -> {
                    val highlightedStatements = contentItem.statements.map { statement ->
                        var highlightedStatement = statement
                        exp.split(" ").forEach { term ->
                            highlightedStatement = highlightedStatement
                                .replace(
                                    "(?i)$term(\\S)|(?i)$term".toRegex(),
                                    "<b>$0</b>"
                                )
                        }
                        highlightedStatement
                    }
                    contentItem.copy(statements = highlightedStatements)
                }

                else -> contentItem
            }
        }
    }

    private fun updateHighlightedContent(content: List<QuestionContent>): List<QuestionContent> {
        content.filterIsInstance<QuestionContent.Text>().forEach {
            it.content = it.content
                .replace("$SMALL_WORD_BETWEEN_TAGS_REGEX|$WHITESPACE_BETWEEN_TAGS_REGEX".toRegex(), "$1")
        }
        content.filterIsInstance<QuestionContent.Statement>().forEach {
            it.statements.forEach { that ->
                that
                    .replace("$SMALL_WORD_BETWEEN_TAGS_REGEX|$WHITESPACE_BETWEEN_TAGS_REGEX".toRegex(), "$1")
            }
        }
        return content
    }

    private fun countContent(exp: String, content: List<QuestionContent>): Int {
        val count = content.filterIsInstance<QuestionContent.Text>()
            .sumOf { exp.toRegex().findAll(it.content).count() }
        LOGGER.debug("Counted {} occurrences of expression in text content.", count)
        return count
    }

    private fun countStatements(exp: String, content: List<QuestionContent>): Int {
        val count = content.filterIsInstance<QuestionContent.Statement>()
            .sumOf { statement -> statement.statements.sumOf { exp.toRegex().findAll(it).count() } }
        LOGGER.debug("Counted {} occurrences of expression in statements content.", count)
        return count
    }

    private fun sortByExpressionCount(list: List<Pair<Question, Long>>): List<Question> {
        LOGGER.info("Sorting list by expression count.")
        return list.sortedByDescending { it.second }
            .map { it.first }
    }
}
