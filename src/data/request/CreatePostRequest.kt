package data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreatePostRequest(
    @JsonProperty("author")
    val author: String?,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("content")
    val content: String,

    @JsonProperty("theme")
    val theme: String?
)