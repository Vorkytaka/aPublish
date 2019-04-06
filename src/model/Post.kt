package model

import org.joda.time.DateTime

data class Post(
    val id: Long,
    val author: String?,
    val title: String,
    val content: String,
    val createdDate: DateTime
)