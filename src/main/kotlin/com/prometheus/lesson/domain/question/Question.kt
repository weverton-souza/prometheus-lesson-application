package com.prometheus.lesson.domain.question

import com.prometheus.lesson.domain.audit.AuditEvent
import com.prometheus.lesson.enumeration.OriginType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "questions")
data class Question(
    @Id
    var id: UUID = UUID.randomUUID(),
    var title: String? = null,
    val source: String? = null,
    val type: String,
    val origin: OriginType,
    val year: Int,
    val comment: String? = null,
    var resources: Resource? = null,
    var content: List<QuestionContent>,
    var answer: AnswerContent?
) : AuditEvent()
