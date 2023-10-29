package com.prometheus.lesson.domain.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

open class AuditEvent(
    @CreatedBy
    var createdBy: UUID? = null,

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),

    var createdByUserName: String? = null,

    @LastModifiedBy
    var updatedBy: UUID? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    var updatedByUserName: String? = null,

    var tenantId: UUID? = null
)
