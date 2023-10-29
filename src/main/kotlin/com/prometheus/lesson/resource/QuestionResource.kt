package com.prometheus.lesson.resource

import com.prometheus.lesson.domain.question.Question
import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import com.prometheus.lesson.payload.question.request.CreateUpdateQuestionTO
import com.prometheus.lesson.payload.question.response.ChatGPTRequest
import com.prometheus.lesson.payload.question.response.QuestionResponseTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Tag(name = "Questions", description = "Resources for managing question")
interface QuestionResource : BaseResource {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Benefit Created Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CreateUpdateQuestionTO::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Create a new question", description = "Provide a question object to create a new question")
    fun create(@RequestBody request: CreateUpdateQuestionTO): Mono<QuestionResponseTO>

    @Operation(
        summary = "Update a question",
        description = "Provide an ID and updated question object to update a question"
    )
    fun update(@PathVariable id: UUID, @RequestBody request: CreateUpdateQuestionTO): Mono<QuestionResponseTO>

    @Operation(summary = "Get a question by ID", description = "Provide an ID to lookup a specific question")
    fun findById(@PathVariable id: UUID): Mono<QuestionResponseTO>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Questions has retrieve Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Question::class)
                    )
                ]
            )
        ]
    )
    @Parameters(
        value = [
            Parameter(
                name = "pageNumber",
                `in` = ParameterIn.QUERY,
                description = "Page number",
                example = "0",
                required = false
            ),
            Parameter(
                name = "pageSize",
                `in` = ParameterIn.QUERY,
                description = "Number of elements per page",
                example = "15",
                required = false
            ),
            Parameter(
                name = "filterBy",
                `in` = ParameterIn.QUERY,
                description = "Filter by ENADE, ENEM, CREATED_BY_ME, CREATED_FROM_MY_INSTITUTION, CREATED_BY_TEACHER",
                example = "ENADE",
                required = false,
                schema = Schema(implementation = FilterByType::class)
            ),
            Parameter(
                name = "orderBy",
                `in` = ParameterIn.QUERY,
                description = "Order by year of creation, or in case of ENADE or ENEM, by year of realization. ASC or DESC",
                example = "DESC",
                schema = Schema(implementation = OrderByType::class),
                required = false
            ),
            Parameter(
                name = "search",
                `in` = ParameterIn.QUERY,
                description = "Search by question content",
                example = "Qual a capital do Brasil?",
                required = false
            ),
            Parameter(
                name = "parameters",
                `in` = ParameterIn.QUERY,
                description = "Search by question content",
                example = "Qual a capital do Brasil?",
                required = false,
                hidden = true
            )
        ]
    )
    @Operation(summary = "Get all questions")
    fun findAll(
        @RequestParam parameters: MutableMap<String, String>
    ): Mono<Page<QuestionResponseTO>>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Questions has retrieve Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = "array",
                            implementation = Question::class
                        )
                    )
                ]
            )
        ]
    )
    fun findAll(
        ids: MutableList<UUID>
    ): Flux<QuestionResponseTO>

    @Operation(summary = "Delete a question by ID", description = "Provide an ID to delete a specific question")
    fun deleteById(@PathVariable id: UUID): Mono<Void>

    @Operation(summary = "Generate question via GPT", description = "Provide a context to generate a question via GPT")
    fun getChatGPTResponse(@RequestBody request: ChatGPTRequest): Mono<Any>
}
