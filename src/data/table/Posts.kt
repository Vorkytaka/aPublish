package data.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Table

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

object Tags : LongIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

class TagEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    companion object : LongEntityClass<TagEntity>(Tags)

    var name by Tags.name
}

object PostTags : Table() {
    val post = reference("post", Posts).primaryKey(0)
    val tag = reference("tag", Tags).primaryKey(1)
}