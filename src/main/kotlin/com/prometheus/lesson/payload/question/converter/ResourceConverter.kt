package com.prometheus.lesson.payload.question.converter

import com.prometheus.lesson.domain.question.Article
import com.prometheus.lesson.domain.question.Book
import com.prometheus.lesson.domain.question.Resource
import com.prometheus.lesson.domain.question.WebSite
import com.prometheus.lesson.payload.question.ArticleTO
import com.prometheus.lesson.payload.question.BookTO
import com.prometheus.lesson.payload.question.ResourceTO
import com.prometheus.lesson.payload.question.WebSiteTO

object ResourceConverter {
    fun toDomain(resourceTO: ResourceTO): Resource {
        return Resource(
            websites = resourceTO.websites.map { WebSite(it.title, it.url) }.toMutableList(),
            books = resourceTO.books.map { Book(it.title, it.author, it.edition, it.chapter) }.toMutableList(),
            articles = resourceTO.articles.map { Article(it.title, it.url, it.isDownloadable, it.fileURl) }.toMutableList()
        )
    }

    fun toTO(resource: Resource): ResourceTO {
        return ResourceTO(
            websites = resource.websites.map { WebSiteTO(it.title, it.url) }.toMutableList(),
            books = resource.books.map { BookTO(it.title, it.author, it.edition, it.chapter) }.toMutableList(),
            articles = resource.articles.map { ArticleTO(it.title, it.url, it.isDownloadable, it.fileURl) }.toMutableList()
        )
    }
}
