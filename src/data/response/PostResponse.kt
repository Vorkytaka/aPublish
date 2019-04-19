package data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PostResponse(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("author")
    val author: String?,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("content")
    val content: String,

    @JsonProperty("createdDate")
    val createdDate: Long,

    @JsonProperty("lang")
    val lang: String?
)