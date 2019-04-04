package data.mapper

import data.response.PostResponse
import data.table.PostEntity
import model.Post

typealias Mapper<From, To> = From.() -> To

val dbMapper: Mapper<PostEntity, Post> = {
    Post(
        this.id.value,
        this.author,
        this.content,
        this.createdDate
    )
}

val responseMapper: Mapper<Post, PostResponse> = {
    PostResponse(
        this.id,
        this.author,
        this.content,
        this.createdDate.millis
    )
}