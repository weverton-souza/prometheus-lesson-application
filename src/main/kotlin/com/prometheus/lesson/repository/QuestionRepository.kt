package com.prometheus.lesson.repository

import com.prometheus.lesson.domain.question.Question
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface QuestionRepository : QuestionPageRepository, ReactiveMongoRepository<Question, UUID>
