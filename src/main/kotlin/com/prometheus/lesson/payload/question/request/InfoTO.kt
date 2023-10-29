package com.prometheus.lesson.payload.question.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "Info about the question.")
data class InfoTO(
    @Schema(description = "User who created the question.")
    var createdBy: UUID? = null,

    @Schema(description = "Date when the question was created.")
    var createdAt: LocalDateTime? = null,

    @Schema(description = "Nickname of the user who created the question.")
    var createdByUserNickname: String? = null,

    @Schema(description = "User who updated the question.")
    var updatedBy: UUID? = null,

    @Schema(description = "Date when the question was updated.")
    var updatedAt: LocalDateTime? = null,

    @Schema(description = "Nickname of the user who updated the question.")
    var updatedByUserNickname: String? = null,

    @Schema(description = "Tenant of the question.")
    var tenantId: UUID? = null
)
