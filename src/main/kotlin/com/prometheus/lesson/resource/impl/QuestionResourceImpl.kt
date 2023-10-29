package com.prometheus.lesson.resource.impl

import com.prometheus.lesson.enumeration.FilterByType
import com.prometheus.lesson.enumeration.OrderByType
import com.prometheus.lesson.payload.question.request.CreateUpdateQuestionTO
import com.prometheus.lesson.payload.question.response.ChatGPTRequest
import com.prometheus.lesson.payload.question.response.QuestionResponseTO
import com.prometheus.lesson.resource.QuestionResource
import com.prometheus.lesson.service.QuestionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/questions")
@Tag(name = "Questions", description = "API for questions")
class QuestionResourceImpl(private val questionService: QuestionService) : QuestionResource {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.CREATED)
    override fun create(@RequestBody request: CreateUpdateQuestionTO): Mono<QuestionResponseTO> =
        this.questionService.save(request, true)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    override fun update(@PathVariable id: UUID, @RequestBody request: CreateUpdateQuestionTO): Mono<QuestionResponseTO> =
        this.questionService.save(request, false)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    override fun findById(@PathVariable id: UUID): Mono<QuestionResponseTO> = this.questionService.findById(id)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    override fun findAll(
        @RequestParam parameters: MutableMap<String, String>
    ): Mono<Page<QuestionResponseTO>> {
        val search = parameters["search"] ?: ""

        val pageable = Pageable
            .ofSize(parameters["pageSize"]?.trim()?.toIntOrNull() ?: 15)
            .withPage(parameters["pageNumber"]?.trim()?.toIntOrNull() ?: 15)

        val filter = if (parameters["filterBy"] != null) {
            FilterByType.valueOf(parameters["filterBy"] as String)
        } else {
            null
        }

        val order = if (parameters["orderBy"] != null) {
            OrderByType.valueOf(parameters["orderBy"] as String)
        } else {
            null
        }

        return this.questionService.findAll(search, pageable, filter, order)
    }

    @GetMapping("/find-all-by-id")
    override fun findAll(@RequestParam ids: MutableList<UUID>): Flux<QuestionResponseTO> {
        return this.questionService.findAll(ids)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteById(@PathVariable id: UUID): Mono<Void> = this.questionService.deleteById(id)

    @PostMapping("/chat-gpt")
    override fun getChatGPTResponse(@RequestBody request: ChatGPTRequest): Mono<Any> {
        return this.questionService.getChatGPTResponse(request)
    }
}
