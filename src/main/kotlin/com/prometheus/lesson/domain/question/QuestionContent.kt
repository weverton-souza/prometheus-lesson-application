package com.prometheus.lesson.domain.question

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * A sealed class representing different types of question content.
 * This class is used for JSON serialization and deserialization.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, property = "valueType")
@JsonSubTypes(
    JsonSubTypes.Type(value = QuestionContent.Text::class, name = "TEXT"),
    JsonSubTypes.Type(value = QuestionContent.Statement::class, name = "STATEMENT"),
    JsonSubTypes.Type(value = QuestionContent.StatementWithAnswer::class, name = "STATEMENT_WITH_ANSWER"),
    JsonSubTypes.Type(value = QuestionContent.Image::class, name = "IMAGE")
)
sealed class QuestionContent {

    /**
     * Subclass representing textual question content.
     * @property valueType Type identifier for TEXT content.
     * @property content The actual text content of the question.
     * @property source Optional source information for the text.
     */
    data class Text(
        var valueType: String,
        var content: String,
        var order: Int = 0,
        var source: String?
    ) : QuestionContent()

    /**
     * Subclass representing question content with a list of statements.
     * @property valueType Type identifier for STATEMENT content.
     * @property statements List of statements in the question.
     * @property source Optional source information for the statements.
     */
    data class Statement(
        var valueType: String,
        var statements: List<String>,
        var order: Int = 0,
        var source: String?
    ) : QuestionContent()

    /**
     * Subclass representing question content with a list of statements and an answer.
     * @property valueType Type identifier for STATEMENT_WITH_ANSWER content.
     * @property option The answer option for the question.
     * @property answer The answer for the question.
     */
    data class StatementWithAnswer(
        var valueType: String = "STATEMENT_WITH_ANSWER",
        var option: String,
        var order: Int = 0,
        var answer: AnswerContent.OpenEnded
    ) : QuestionContent()

    /**
     * Subclass representing question content with an image.
     * @property valueType Type identifier for IMAGE content.
     * @property url URL pointing to the image.
     * @property source Optional source information for the image.
     */
    data class Image(
        var valueType: String,
        val caption: String = "",
        val details: String = "",
        var url: String,
        var order: Int = 0,
        var source: String?
    ) : QuestionContent()
}
