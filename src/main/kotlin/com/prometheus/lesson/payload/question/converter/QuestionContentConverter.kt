package com.prometheus.lesson.payload.question.converter

import com.prometheus.lesson.domain.question.AnswerContent
import com.prometheus.lesson.domain.question.QuestionContent
import com.prometheus.lesson.payload.question.AnswerContentTO
import com.prometheus.lesson.payload.question.QuestionContentTO
import java.util.UUID

object QuestionContentConverter {

    fun toDomain(questionContentTO: QuestionContentTO): QuestionContent {
        return when (questionContentTO) {
            is QuestionContentTO.TextTO -> QuestionContent.Text(
                valueType = questionContentTO.valueType,
                content = questionContentTO.content,
                order = questionContentTO.order,
                source = questionContentTO.source
            )
            is QuestionContentTO.StatementTO -> QuestionContent.Statement(
                valueType = questionContentTO.valueType,
                statements = questionContentTO.statements,
                order = questionContentTO.order,
                source = questionContentTO.source
            )
            is QuestionContentTO.ImageTO -> QuestionContent.Image(
                valueType = questionContentTO.valueType,
                url = questionContentTO.url,
                order = questionContentTO.order,
                caption = questionContentTO.caption,
                details = questionContentTO.details,
                source = questionContentTO.source
            )
            is QuestionContentTO.StatementWithAnswerTO -> QuestionContent.StatementWithAnswer(
                valueType = questionContentTO.valueType,
                option = questionContentTO.option,
                order = questionContentTO.order,
                answer = AnswerContent.OpenEnded(
                    valueType = questionContentTO.answer.valueType,
                    code = UUID.randomUUID(),
                    characterLimit = questionContentTO.answer.characterLimit
                )
            )
        }
    }

    fun toTO(questionContent: QuestionContent): QuestionContentTO {
        return when (questionContent) {
            is QuestionContent.Text -> QuestionContentTO.TextTO(
                valueType = questionContent.valueType,
                content = questionContent.content,
                order = questionContent.order,
                source = questionContent.source
            )
            is QuestionContent.Statement -> QuestionContentTO.StatementTO(
                valueType = questionContent.valueType,
                statements = questionContent.statements,
                source = questionContent.source
            )
            is QuestionContent.Image -> QuestionContentTO.ImageTO(
                valueType = questionContent.valueType,
                url = questionContent.url,
                order = questionContent.order,
                caption = questionContent.caption,
                details = questionContent.details,
                source = questionContent.source
            )
            is QuestionContent.StatementWithAnswer -> QuestionContentTO.StatementWithAnswerTO(
                valueType = questionContent.valueType,
                option = questionContent.option,
                order = questionContent.order,
                answer = AnswerContentTO.OpenEndedTO(
                    valueType = questionContent.answer.valueType,
                    code = questionContent.answer.code,
                    characterLimit = questionContent.answer.characterLimit
                )
            )
        }
    }
}
