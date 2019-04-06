package data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PageResponse(
    @JsonProperty("page")
    val page: Int,

    @JsonProperty("posts")
    val posts: List<PostResponse>,

    @JsonProperty("hasNextPage")
    val hasNextPage: Boolean
)