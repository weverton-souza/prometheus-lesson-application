package com.prometheus.lesson.payload.question.converter

import com.prometheus.lesson.domain.question.AnswerContent
import com.prometheus.lesson.payload.question.AnswerContentTO

object AnswerContentConverter {

    fun toDomain(answerContentTO: AnswerContentTO): AnswerContent {
        return when (answerContentTO) {
            is AnswerContentTO.MultipleChoiceTO -> AnswerContent.MultipleChoice(
                valueType = answerContentTO.valueType,
                order = answerContentTO.order,
                answers = answerContentTO.answers.map {
                    AnswerContent.MultipleChoiceAnswer(
                        answer = it.answer,
                        comment = it.comment,
                        isCorrect = it.isCorrect
                    )
                }.toMutableList()
            )
            is AnswerContentTO.OpenEndedTO -> AnswerContent.OpenEnded(
                valueType = answerContentTO.valueType,
                order = answerContentTO.order,
                characterLimit = 1000
            )
            is AnswerContentTO.TrueOrFalseTO -> AnswerContent.TrueOrFalse(
                valueType = answerContentTO.valueType,
                order = answerContentTO.order,
                answer = answerContentTO.answer,
                comment = answerContentTO.comment,
                isCorrect = answerContentTO.isCorrect
            )
            is AnswerContentTO.TrueOrFalseCollectionTO -> AnswerContent.TrueOrFalseCollection(
                trueOrFalseCollection = answerContentTO.trueOrFalseCollection.map {
                    AnswerContent.TrueOrFalse(
                        valueType = it.valueType,
                        order = answerContentTO.order,
                        answer = it.answer,
                        comment = it.comment,
                        isCorrect = it.isCorrect
                    )
                }.toMutableList()
            )
            is AnswerContentTO.FillInTheBlankTO -> AnswerContent.FillInTheBlank(
                valueType = answerContentTO.valueType,
                order = answerContentTO.order,
                blanks = answerContentTO.blanks
            )
            is AnswerContentTO.MatchingTO -> AnswerContent.Matching(
                valueType = answerContentTO.valueType,
                order = answerContentTO.order,
                matches = answerContentTO.matches
            )
        }
    }

    fun toTO(answerContent: AnswerContent): AnswerContentTO {
        return when (answerContent) {
            is AnswerContent.OpenEnded -> AnswerContentTO.OpenEndedTO(
                valueType = answerContent.valueType,
                characterLimit = answerContent.characterLimit
            )
            is AnswerContent.MultipleChoice -> AnswerContentTO.MultipleChoiceTO(
                valueType = answerContent.valueType,
                code = answerContent.code,
                order = answerContent.order,
                answers = answerContent.answers.map {
                    AnswerContentTO.MultipleChoiceAnswerRequest(it.code, it.answer, it.comment, it.isCorrect)
                }
            )
            is AnswerContent.TrueOrFalse -> AnswerContentTO.TrueOrFalseTO(
                valueType = answerContent.valueType,
                code = answerContent.code,
                order = answerContent.order,
                answer = answerContent.answer,
                comment = answerContent.comment,
                isCorrect = answerContent.isCorrect
            )
            is AnswerContent.TrueOrFalseCollection -> AnswerContentTO.TrueOrFalseCollectionTO(
                trueOrFalseCollection = answerContent.trueOrFalseCollection.map {
                    AnswerContentTO.TrueOrFalseTO(
                        valueType = it.valueType,
                        code = it.code,
                        order = answerContent.order,
                        answer = it.answer,
                        comment = it.comment,
                        isCorrect = it.isCorrect
                    )
                }.toMutableList()
            )
            is AnswerContent.FillInTheBlank -> AnswerContentTO.FillInTheBlankTO(
                valueType = answerContent.valueType,
                code = answerContent.code,
                order = answerContent.order,
                blanks = answerContent.blanks
            )
            is AnswerContent.Matching -> AnswerContentTO.MatchingTO(
                valueType = answerContent.valueType,
                code = answerContent.code,
                order = answerContent.order,
                matches = answerContent.matches
            )
        }
    }
}
