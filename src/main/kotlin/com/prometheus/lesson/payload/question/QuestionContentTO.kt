package com.prometheus.lesson.payload.question

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "valueType")
@JsonSubTypes(
    JsonSubTypes.Type(value = QuestionContentTO.TextTO::class, name = "TEXT"),
    JsonSubTypes.Type(value = QuestionContentTO.StatementTO::class, name = "STATEMENT"),
    JsonSubTypes.Type(value = QuestionContentTO.ImageTO::class, name = "IMAGE")
)
@Schema(description = "Base class for question content with various types.")
sealed class QuestionContentTO {

    @Schema(description = "Text type content for a question.")
    data class TextTO(
        @Schema(description = "Type of the content.")
        val valueType: String,

        @Schema(description = "Actual content of the text.")
        val content: String,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Source of the content, if available.")
        val source: String?
    ) : QuestionContentTO()

    @Schema(description = "Statement type content for a question.")
    data class StatementTO(
        val valueType: String,

        @Schema(description = "List of statements.")
        val statements: List<String>,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Source of the content, if available.")
        val source: String?
    ) : QuestionContentTO()

    @Schema(description = "Statement with answer type content for a question.")
    data class StatementWithAnswerTO(
        @Schema(description = "Type of the content.")
        val valueType: String,

        @Schema(description = "Answer option for the question.")
        val option: String,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Answer for the question.")
        val answer: AnswerContentTO.OpenEndedTO
    ) : QuestionContentTO()

    @Schema(description = "Image type content for a question.")
    data class ImageTO(
        val valueType: String,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Description of the image.")
        val caption: String = "",

        @Schema(description = "URL of the image.")
        val url: String,

        @Schema(description = "Details about the image")
        val details: String = "",

        @Schema(description = "Source of the content, if available.")
        val source: String?
    ) : QuestionContentTO()
}
