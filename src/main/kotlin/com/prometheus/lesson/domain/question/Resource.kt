package com.prometheus.lesson.domain.question

data class Resource(
    var websites: MutableList<WebSite>,
    var books: MutableList<Book>,
    var articles: MutableList<Article>
)

data class WebSite(
    var title: String,
    var url: String
)

data class Book(
    var title: String,
    var author: String? = null,
    var edition: String? = null,
    var chapter: String? = null
)

data class Article(
    var title: String,
    var url: String? = null,
    var isDownloadable: Boolean = false,
    var fileURl: String? = null
)
