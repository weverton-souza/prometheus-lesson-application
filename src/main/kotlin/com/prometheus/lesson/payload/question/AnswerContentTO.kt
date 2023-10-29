package com.prometheus.lesson.payload.question

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "valueType")
@JsonSubTypes(
    JsonSubTypes.Type(value = AnswerContentTO.MultipleChoiceTO::class, name = "MULTIPLE_CHOICE"),
    JsonSubTypes.Type(value = AnswerContentTO.OpenEndedTO::class, name = "OPEN_ENDED"),
    JsonSubTypes.Type(value = AnswerContentTO.TrueOrFalseTO::class, name = "TRUE_OR_FALSE"),
    JsonSubTypes.Type(value = AnswerContentTO.TrueOrFalseCollectionTO::class, name = "TRUE_OR_FALSE_COLLECTION"),
    JsonSubTypes.Type(value = AnswerContentTO.MatchingTO::class, name = "MATCHING"),
    JsonSubTypes.Type(value = AnswerContentTO.FillInTheBlankTO::class, name = "FILL_IN_THE_BLANK")
)
@Schema(description = "Base class for answer content with different types.")
sealed class AnswerContentTO {

    @Schema(description = "Open ended type answer.")
    data class OpenEndedTO(
        val valueType: String,
        val code: UUID? = null,
        var order: Int = 0,
        var characterLimit: Int = 1000
    ) : AnswerContentTO()

    @Schema(description = "Multiple choice type answer.")
    data class MultipleChoiceTO(
        val valueType: String,

        @Schema(description = "Unique identifier for the answer choice.")
        val code: UUID? = null,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "List of potential answers for multiple choice.")
        val answers: List<MultipleChoiceAnswerRequest>
    ) : AnswerContentTO()

    @Schema(description = "True or false type answer.")
    data class TrueOrFalseTO(
        @Schema(description = "Type of the answer.")
        val valueType: String,

        @Schema(description = "Unique identifier for the answer choice.")
        val code: UUID? = null,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Provided answer as string.")
        val answer: String,

        @Schema(description = "Comment for the answer.")
        val comment: String? = null,

        @Schema(description = "Flag to indicate if the provided answer is correct.")
        val isCorrect: Boolean
    ) : AnswerContentTO()

    @Schema(description = "True or false collection type answer.")
    data class TrueOrFalseCollectionTO(
        @Schema(description = "List of true or false answers.")
        val trueOrFalseCollection: MutableList<TrueOrFalseTO>,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0
    ) : AnswerContentTO()

    @Schema(description = "Matching type answer.")
    data class MatchingTO(
        @Schema(description = "Type of the answer.")
        val valueType: String,

        @Schema(description = "Unique identifier for the answer choice.")
        val code: UUID? = null,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Map of matches for the answer.")
        val matches: Map<String, String>
    ) : AnswerContentTO()

    @Schema(description = "Fill in the blank type answer.")
    data class FillInTheBlankTO(
        @Schema(description = "Type of the answer.")
        val valueType: String,

        @Schema(description = "Unique identifier for the answer choice.")
        val code: UUID? = null,

        @Schema(description = "Order of the answer choice.")
        var order: Int = 0,

        @Schema(description = "Map of blank spots and their corresponding answers.")
        val blanks: Map<Int, String>
    ) : AnswerContentTO()

    @Schema(description = "Details about a specific multiple choice answer.")
    data class MultipleChoiceAnswerRequest(
        @Schema(description = "Unique identifier for the answer choice.")
        val code: UUID? = null,

        @Schema(description = "Text of the answer choice.")
        val answer: String,

        @Schema(description = "Comment for the answer.")
        val comment: String? = null,

        @Schema(description = "Flag to indicate if this choice is the correct answer.")
        val isCorrect: Boolean
    )
}
