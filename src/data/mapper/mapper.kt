package data.mapper

import data.response.PostResponse
import data.table.PostEntity
import model.Post

typealias Mapper<From, To> = From.() -> To

val dbMapper: Mapper<PostEntity, Post> = {
    Post(
        this.id.value,
        this.author,
        this.title,
        this.content,
        this.createdDate,
        this.theme
    )
}

val responseMapper: Mapper<Post, PostResponse> = {
    PostResponse(
        this.id,
        this.author,
        this.title,
        this.content,
        this.createdDate.millis,
        this.theme
    )
}