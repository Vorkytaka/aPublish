package data.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object Posts : LongIdTable() {
    val author = varchar("author", 100).nullable()
    val content = varchar("content", 5000)
    val createdDate = datetime("createdDate")
}

class PostEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    companion object : LongEntityClass<PostEntity>(Posts)

    var author by Posts.author
    var content by Posts.content
    var createdDate by Posts.createdDate
}