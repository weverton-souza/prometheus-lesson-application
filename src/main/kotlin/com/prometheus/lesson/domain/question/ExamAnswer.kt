package com.prometheus.lesson.domain.question

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.prometheus.lesson.enumeration.QuestionType
import java.util.UUID

data class QuestionInfo(
    val id: UUID,
    val type: QuestionType
)

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "QuestionInfo.type")
@JsonSubTypes(
    JsonSubTypes.Type(value = ExamAnswer.MultipleChoice::class, name = "MULTIPLE_CHOICE"),
    JsonSubTypes.Type(value = ExamAnswer.ShortOpenEnded::class, name = "SHORT_OPEN_ENDED"),
    JsonSubTypes.Type(value = ExamAnswer.LongOpenEnded::class, name = "LONG_OPEN_ENDED"),
    JsonSubTypes.Type(value = ExamAnswer.TrueOrFalse::class, name = "TRUE_OR_FALSE"),
    JsonSubTypes.Type(value = ExamAnswer.TrueOrFalseCollection::class, name = "TRUE_OR_FALSE_COLLECTION")
)
sealed class ExamAnswer {

    data class ShortOpenEnded(
        var questionInfo: QuestionInfo = QuestionInfo(UUID.randomUUID(), QuestionType.SHORT_OPEN_ENDED),
        var characterLimit: Int = 1000
    ) : ExamAnswer()

    data class LongOpenEnded(
        var questionInfo: QuestionInfo = QuestionInfo(UUID.randomUUID(), QuestionType.LONG_OPEN_ENDED),
        var characterLimit: Int = 5000
    ) : ExamAnswer()

    data class MultipleChoice(
        var questionInfo: QuestionInfo = QuestionInfo(UUID.randomUUID(), QuestionType.MULTIPLE_CHOICE),
        var code: String,
        var answer: Boolean,
        val isCorrect: Boolean
    ) : ExamAnswer()

    data class TrueOrFalse(
        var questionInfo: QuestionInfo = QuestionInfo(UUID.randomUUID(), QuestionType.TRUE_OR_FALSE),
        var code: String,
        var answer: Boolean,
        val isCorrect: Boolean
    ) : ExamAnswer()

    data class TrueOrFalseCollection(
        var questionInfo: QuestionInfo = QuestionInfo(UUID.randomUUID(), QuestionType.TRUE_OR_FALSE_COLLECTION),
        var answers: MutableList<TrueOrFalseAnswer> = mutableListOf()
    ) : ExamAnswer()

    data class TrueOrFalseAnswer(
        var code: String,
        var answer: Boolean,
        val isCorrect: Boolean
    )
}
