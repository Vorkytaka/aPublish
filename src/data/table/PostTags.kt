package data.table

import org.jetbrains.exposed.sql.Table

object PostTags : Table() {
    val post = reference("post", Posts).primaryKey(0)
    val tag = reference("tag", Tags).primaryKey(1)
}