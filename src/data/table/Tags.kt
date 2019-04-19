package data.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object Tags : LongIdTable() {
    val name = varchar("name", 64).uniqueIndex()
}

class TagEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    companion object : LongEntityClass<TagEntity>(Tags)

    var name by Tags.name
}