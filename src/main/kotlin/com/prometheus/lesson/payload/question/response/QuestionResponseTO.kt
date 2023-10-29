package com.prometheus.lesson.payload.question.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.prometheus.lesson.enumeration.OriginType
import com.prometheus.lesson.payload.question.AnswerContentTO
import com.prometheus.lesson.payload.question.QuestionContentTO
import com.prometheus.lesson.payload.question.ResourceTO
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Details about a question.")
data class QuestionResponseTO(
    @Schema(description = "Unique identifier for the question.")
    var id: UUID? = UUID.randomUUID(),

    @Schema(description = "Title identifier for the question.")
    val title: String,

    @Schema(description = "Description of the question.")
    val resources: ResourceTO? = null,

    @Schema(description = "Explanation for the question.")
    val comment: String? = null,

    @Schema(description = "Type of the question. MULTIPLE_CHOICE, TRUE_OR_FALSE, MATCHING, FILL_IN_THE_BLANK")
    val type: String,

    @Schema(description = "Origin of the question. ENADE, ENEM")
    val origin: OriginType,

    @Schema(description = "Year of the origin of the question.")
    val year: Int,

    @Schema(description = "User who created the question. It is a external reference.")
    var info: InfoResponseTO? = null,

    @Schema(description = "Contents of the question.")
    val content: List<QuestionContentTO> = listOf(),

    @Schema(description = "Answer to the question.")
    val answer: AnswerContentTO? = null
)
