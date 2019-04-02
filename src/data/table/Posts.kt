package data.table

import org.jetbrains.exposed.sql.Table

object Posts : Table() {
    val id = long("id").primaryKey().autoIncrement()
    val author = varchar("author", 100).nullable()
    val content = varchar("content", 5000)
    val createdDate = datetime("createdDate")
}