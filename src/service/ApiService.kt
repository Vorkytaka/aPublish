package service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.NewPost
import model.Post
import model.Posts
import model.toPost
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

interface IApiService {
    suspend fun getAllPosts(): List<Post>
    suspend fun getPost(id: Long): Post?
    suspend fun addPost(post: NewPost): Post
}

class ApiService : IApiService {
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
}