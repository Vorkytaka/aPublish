package model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object Posts : Table() {
    val id = long("id").primaryKey().autoIncrement()
    val author = varchar("author", 100).nullable()
    val content = varchar("content", 5000)
    val createdDate = datetime("createdDate")
}

data class Post(
    val id: Long,
    val author: String?,
    val content: String,
    val createdDate: DateTime
)

data class NewPost(
    val author: String?,
    val content: String
)

fun ResultRow.toPost() = Post(
    this[Posts.id],
    this[Posts.author],
    this[Posts.content],
    this[Posts.createdDate]
)