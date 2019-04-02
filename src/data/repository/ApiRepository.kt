package data.repository

import data.table.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.NewPost
import model.Post
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ApiRepository : IApiRepository {
    override suspend fun getAllPosts(): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            Posts.selectAll().map { it.toPost() }
        }
    }

    override suspend fun getPost(id: Long): Post? = withContext(Dispatchers.IO) {
        if (id == -1L) return@withContext null

        transaction {
            Posts.select { (Posts.id eq id) }
                .map { it.toPost() }
                .firstOrNull()
        }
    }

    override suspend fun addPost(post: NewPost): Post = withContext(Dispatchers.IO) {
        var id: Long = -1L
        transaction {
            id = (Posts.insert {
                it[author] = post.author
                it[content] = post.content
                it[createdDate] = DateTime.now()
            } get Posts.id)!!
        }

        getPost(id)!!
    }

    fun ResultRow.toPost() = Post(
        this[Posts.id],
        this[Posts.author],
        this[Posts.content],
        this[Posts.createdDate]
    )
}