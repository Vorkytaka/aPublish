package data.mapper

import data.response.PostResponse
import data.table.PostEntity
import model.Post

typealias Mapper<From, To> = From.() -> To

fun PostEntity.mapToPost() =
    Post(
        this.id.value,
        this.author,
        this.title,
        this.content,
        this.createdDate,
        this.language
    )

fun Post.mapToPostResponse() =
    PostResponse(
        this.id,
        this.author,
        this.title,
        this.content,
        this.createdDate.millis,
        this.language
    )