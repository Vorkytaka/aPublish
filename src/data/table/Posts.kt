package data.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object Posts : LongIdTable() {
    val author = varchar("author", 64).nullable()
    val title = varchar("title", 64)
    val content = text("content")
    val createdDate = datetime("createdDate")
    val language = varchar("language", 2).nullable()
}

class PostEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    companion object : LongEntityClass<PostEntity>(Posts)

    var author by Posts.author
    var title by Posts.title
    var content by Posts.content
    var createdDate by Posts.createdDate
    var language by Posts.language
    var tags by TagEntity via PostTags
}
