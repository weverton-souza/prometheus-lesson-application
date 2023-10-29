package com.prometheus.lesson.payload.question

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Details about a resource. Resource can be a website, book or article")
data class ResourceTO(
    @Schema(description = "List of websites.")
    var websites: MutableList<WebSiteTO>,

    @Schema(description = "List of books.")
    var books: MutableList<BookTO>,

    @Schema(description = "List of articles.")
    var articles: MutableList<ArticleTO>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Details about a website.")
data class WebSiteTO(
    @Schema(description = "Title of the website.")
    var title: String,

    @Schema(description = "URL of the website.")
    var url: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Details about a book.")
data class BookTO(
    @Schema(description = "Title of the book.")
    var title: String,

    @Schema(description = "Author of the book.")
    var author: String? = null,

    @Schema(description = "Edition of the book.")
    var edition: String? = null,

    @Schema(description = "Chapter of the book.")
    var chapter: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Details about an article.")
data class ArticleTO(
    @Schema(description = "Title of the article.")
    var title: String,

    @Schema(description = "URL of the article.")
    var url: String? = null,

    @Schema(description = "Article is downloadable or not.")
    var isDownloadable: Boolean = false,

    @Schema(description = "URL of the file. If article is downloadable, this field is required.")
    var fileURl: String? = null
)
