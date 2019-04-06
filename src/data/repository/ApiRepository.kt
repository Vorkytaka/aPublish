package data.repository

import POST_ON_PAGE_COUNT
import data.mapper.Mapper
import data.request.CreatePostRequest
import data.table.PostEntity
import data.table.Posts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Post
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ApiRepository(
    private val mapper: Mapper<PostEntity, Post>
) : IApiRepository {

    override suspend fun getPage(page: Int): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            PostEntity.all().limit(POST_ON_PAGE_COUNT + 1, POST_ON_PAGE_COUNT * page).map(mapper)
        }
    }

    override suspend fun getPost(id: Long): Post? = withContext(Dispatchers.IO) {
        if (id == -1L) return@withContext null

        transaction {
            PostEntity.find { Posts.id eq id }
                .map(mapper)
                .firstOrNull()
        }
    }

    override suspend fun addPost(post: CreatePostRequest): Post = withContext(Dispatchers.IO) {
        var id: Long = -1L
        transaction {
            id = (PostEntity.new {
                author = post.author
                title = post.title
                content = post.content
                createdDate = DateTime.now()
            }).id.value
        }

        getPost(id)!!
    }
}