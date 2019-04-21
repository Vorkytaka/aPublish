package model

import org.joda.time.DateTime

data class Post(
    val id: Long,
    val author: String?,
    val title: String,
    val content: String,
    val createdDate: DateTime,
    val language: String?,
    val tags: Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}