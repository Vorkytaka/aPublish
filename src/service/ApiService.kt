package service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Post
import model.Posts
import model.toPost
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface IApiService {
    suspend fun getAllPosts(): List<Post>
    suspend fun getPost(id: Long): Post?
}

class ApiService : IApiService {
    override suspend fun getAllPosts(): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            Posts.selectAll().map { it.toPost() }
        }
    }

    override suspend fun getPost(id: Long): Post? = withContext(Dispatchers.IO) {
        transaction {
            Posts.select { (Posts.id eq id) }
                .map { it.toPost() }
                .firstOrNull()
        }
    }
}