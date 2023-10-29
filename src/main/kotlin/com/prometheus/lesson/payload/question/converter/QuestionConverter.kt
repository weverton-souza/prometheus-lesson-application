package com.prometheus.lesson.payload.question.converter

import com.prometheus.lesson.domain.question.Question
import com.prometheus.lesson.payload.question.request.CreateUpdateQuestionTO
import com.prometheus.lesson.payload.question.response.CreatedInfoTO
import com.prometheus.lesson.payload.question.response.InfoResponseTO
import com.prometheus.lesson.payload.question.response.QuestionResponseTO
import com.prometheus.lesson.payload.question.response.UpdatedInfoTO
import java.time.LocalDateTime
import java.util.UUID

object QuestionConverter {

    fun toQuestion(createUpdateQuestionTO: CreateUpdateQuestionTO, isNewQuestion: Boolean = true): Question {
        val question = Question(
            id = createUpdateQuestionTO.id ?: UUID.randomUUID(),
            title = createUpdateQuestionTO.title,
            comment = createUpdateQuestionTO.comment,
            type = createUpdateQuestionTO.type,
            origin = createUpdateQuestionTO.origin,
            year = createUpdateQuestionTO.year ?: LocalDateTime.now().year,
            resources = createUpdateQuestionTO.resources?.let { ResourceConverter.toDomain(it) },
            content = createUpdateQuestionTO.content.map { QuestionContentConverter.toDomain(it) },
            answer = createUpdateQuestionTO.answer?.let { AnswerContentConverter.toDomain(it) }
        )

        if (isNewQuestion) {
            question.createdBy = createUpdateQuestionTO.info?.createdBy
            question.createdAt = LocalDateTime.now()
            question.createdByUserName = createUpdateQuestionTO.info?.createdByUserNickname
            question.tenantId = createUpdateQuestionTO.info?.tenantId
        } else {
            question.updatedBy = createUpdateQuestionTO.info?.updatedBy
            question.updatedByUserName = createUpdateQuestionTO.info?.updatedByUserNickname
            question.updatedAt = LocalDateTime.now()
        }
        return question
    }

    fun toQuestionResponseTO(question: Question): QuestionResponseTO {
        val responseTO = QuestionResponseTO(
            id = question.id,
            title = question.title,
            comment = question.comment,
            type = question.type,
            origin = question.origin,
            year = question.year,
            content = question.content.map { QuestionContentConverter.toTO(it) },
            resources = question.resources?.let { ResourceConverter.toTO(it) },
            answer = question.answer?.let { AnswerContentConverter.toTO(it) }
        )

        if (question.updatedBy == null) {
            responseTO.info = InfoResponseTO(
                createdInfo = CreatedInfoTO(
                    id = question.createdBy,
                    name = question.createdByUserName,
                    createdAt = question.createdAt
                ),
                updatedInfo = null
            )
        } else {
            responseTO.info = InfoResponseTO(
                createdInfo = CreatedInfoTO(
                    id = question.createdBy,
                    name = question.createdByUserName,
                    createdAt = question.createdAt
                ),
                updatedInfo = UpdatedInfoTO(
                    id = question.updatedBy,
                    name = question.updatedByUserName,
                    updatedAt = question.updatedAt
                )
            )
        }
        return responseTO
    }
}
