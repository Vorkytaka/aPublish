package data.repository

import data.request.CreatePostRequest
import model.Post

interface IApiRepository {
    suspend fun getPage(page: Int): List<Post>
    suspend fun findPostById(id: Long): Post?
    suspend fun addPost(post: CreatePostRequest): Post
    suspend fun findPostsByTheme(theme: String, page: Int): List<Post>
    suspend fun findPostsByAuthor(author: String, page: Int): List<Post>
}