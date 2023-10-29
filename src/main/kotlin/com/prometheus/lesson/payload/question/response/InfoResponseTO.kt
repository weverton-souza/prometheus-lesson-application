package com.prometheus.lesson.payload.question.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class InfoResponseTO(
    val createdInfo: CreatedInfoTO? = null,
    val updatedInfo: UpdatedInfoTO? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreatedInfoTO(
    var id: UUID? = null,
    var name: String? = null,
    var createdAt: LocalDateTime? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdatedInfoTO(
    var id: UUID? = null,
    var name: String? = null,
    var updatedAt: LocalDateTime?
)
