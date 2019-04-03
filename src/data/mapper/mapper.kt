package data.mapper

import data.response.PostResponse
import data.table.Posts
import model.Post
import org.jetbrains.exposed.sql.ResultRow

typealias Mapper<From, To> = From.() -> To

val dbMapper: Mapper<ResultRow, Post> = {
    Post(
        this[Posts.id],
        this[Posts.author],
        this[Posts.content],
        this[Posts.createdDate]
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