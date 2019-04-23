package data.repository

import data.request.NewPostRequest
import model.Post

interface IApiRepository {
    suspend fun getPage(page: Int): List<Post>
    suspend fun findPostById(id: Long): Post?
    suspend fun addPost(post: NewPostRequest): Post
    suspend fun findPostsByAuthor(author: String, page: Int): List<Post>
    suspend fun findPostsByTag(tag: String, page: Int): List<Post>
}