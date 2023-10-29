package com.prometheus.lesson.domain.question

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.UUID

/**
 * The `AnswerContent` class represents different types of answer content for questions in a serialized format.
 * It is designed for use with JSON serialization and deserialization and includes subclasses
 * for multiple-choice, true-or-false, matching, and fill-in-the-blank questions.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "valueType")
@JsonSubTypes(
    JsonSubTypes.Type(value = AnswerContent.MultipleChoice::class, name = "MULTIPLE_CHOICE"),
    JsonSubTypes.Type(value = AnswerContent.OpenEnded::class, name = "OPEN_ENDED"),
    JsonSubTypes.Type(value = AnswerContent.TrueOrFalse::class, name = "TRUE_OR_FALSE"),
    JsonSubTypes.Type(value = AnswerContent.TrueOrFalseCollection::class, name = "TRUE_OR_FALSE_COLLECTION"),
    JsonSubTypes.Type(value = AnswerContent.Matching::class, name = "MATCHING"),
    JsonSubTypes.Type(value = AnswerContent.FillInTheBlank::class, name = "FILL_IN_THE_BLANK")
)
sealed class AnswerContent {

    /**
     * Represents answer content for short open-ended questions.
     * @property valueType Type identifier for SHORT_OPEN_ENDED content.
     * @property characterLimit Maximum number of characters allowed for the answer.
     */
    data class OpenEnded(
        var valueType: String,
        var code: UUID = UUID.randomUUID(),
        var order: Int = 0,
        var characterLimit: Int = 1000
    ) : AnswerContent()

    /**
     * Represents answer content for multiple-choice questions.
     * @property valueType Type identifier for MULTIPLE_CHOICE content.
     * @property code Unique identifier for the answer content.
     * @property answers List of multiple-choice answers.
     */
    data class MultipleChoice(
        var valueType: String,
        var code: UUID = UUID.randomUUID(),
        var order: Int = 0,
        var answers: List<MultipleChoiceAnswer>
    ) : AnswerContent()

    /**
     * Represents answer content for true-or-false questions.
     * @property valueType Type identifier for TRUE_OR_FALSE content.
     * @property code Unique identifier for the answer content.
     * @property answer The true or false answer text.
     * @property isCorrect Indicates whether the answer is correct (true) or not (false).
     */
    data class TrueOrFalse(
        var valueType: String,
        var code: UUID = UUID.randomUUID(),
        var order: Int = 0,
        var answer: String,
        var comment: String? = null,
        val isCorrect: Boolean
    ) : AnswerContent()

    data class TrueOrFalseCollection(
        var valueType: String = "TRUE_OR_FALSE_COLLECTION",
        var order: Int = 0,
        var trueOrFalseCollection: MutableList<TrueOrFalse> = mutableListOf()
    ) : AnswerContent() {
        fun add(trueOrFalse: TrueOrFalse): TrueOrFalseCollection {
            this.trueOrFalseCollection.add(trueOrFalse)
            return this
        }

        fun add(trueOrFalseCollection: MutableList<TrueOrFalse>): TrueOrFalseCollection {
            this.trueOrFalseCollection.addAll(trueOrFalseCollection)
            return this
        }
    }

    /**
     * Represents answer content for matching questions.
     * @property valueType Type identifier for MATCHING content.
     * @property code Unique identifier for the answer content.
     * @property matches A map of items to be matched (e.g., item A matches item B).
     */
    data class Matching(
        var valueType: String,
        var code: UUID = UUID.randomUUID(),
        var order: Int = 0,
        var matches: Map<String, String>
    ) : AnswerContent()

    /**
     * Represents answer content for fill-in-the-blank questions.
     * @property valueType Type identifier for FILL_IN_THE_BLANK content.
     * @property code Unique identifier for the answer content.
     * @property blanks A map of blank positions and their corresponding answers.
     */
    data class FillInTheBlank(
        var valueType: String,
        var code: UUID = UUID.randomUUID(),
        var order: Int = 0,
        var blanks: Map<Int, String>
    ) : AnswerContent()

    /**
     * Represents an answer option for multiple-choice questions.
     * @property code Unique identifier for the answer option.
     * @property answer The text of the answer option.
     * @property isCorrect Indicates whether the answer option is correct (true) or not (false).
     */
    data class MultipleChoiceAnswer(
        var code: UUID = UUID.randomUUID(),
        var answer: String,
        var comment: String? = null,
        var isCorrect: Boolean
    )
}
