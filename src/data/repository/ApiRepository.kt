package data.repository

import POST_ON_PAGE_COUNT
import data.mapper.Mapper
import data.request.NewPostRequest
import data.table.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Post
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ApiRepository(
    private val mapper: Mapper<PostEntity, Post>
) : IApiRepository {

    override suspend fun getPage(page: Int): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            Posts.selectAll()
                .orderBy(Posts.id to SortOrder.DESC)
                .limit(POST_ON_PAGE_COUNT + 1, POST_ON_PAGE_COUNT * page)
                .map { PostEntity.wrapRow(it) }
                .map(mapper)
        }
    }

    override suspend fun findPostById(id: Long): Post? = withContext(Dispatchers.IO) {
        if (id == -1L) return@withContext null

        transaction {
            PostEntity.find { Posts.id eq id }
                .map(mapper)
                .firstOrNull()
        }
    }

    override suspend fun addPost(post: NewPostRequest): Post = withContext(Dispatchers.IO) {
        val createdPost = transaction {
            PostEntity.new {
                author = post.author
                title = post.title
                content = post.content
                createdDate = DateTime.now()
                language = post.lang // todo: validate
            }
        }

        if (post.tags != null) {
            val tags = transaction {
                post.tags
                    .map {
                        TagEntity.find { Tags.name eq it }.firstOrNull() ?: TagEntity.new { name = it }
                    }
            }

            transaction {
                createdPost.tags = SizedCollection(tags)
            }
        }

        findPostById(createdPost.id.value)!!
    }

    override suspend fun findPostsByAuthor(author: String, page: Int): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            Posts.select { Posts.author eq author }
                .orderBy(Posts.id to SortOrder.DESC)
                .limit(POST_ON_PAGE_COUNT + 1, POST_ON_PAGE_COUNT * page)
                .map { PostEntity.wrapRow(it) }
                .map(mapper)
        }
    }

    override suspend fun findPostsByTag(tag: String, page: Int): List<Post> = withContext(Dispatchers.IO) {
        transaction {
            PostTags
                .innerJoin(Posts)
                .innerJoin(Tags)
                .select { Tags.name eq tag }
                .orderBy(Posts.id to SortOrder.DESC)
                .limit(POST_ON_PAGE_COUNT + 1, POST_ON_PAGE_COUNT * page)
                .reversed()
                .map { PostEntity.wrapRow(it) }
                .map(mapper)
        }
    }
}