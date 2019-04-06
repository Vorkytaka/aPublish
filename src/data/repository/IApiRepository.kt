package data.repository

import data.request.CreatePostRequest
import model.Post

interface IApiRepository {
    suspend fun getPage(page: Int): List<Post>
    suspend fun getPost(id: Long): Post?
    suspend fun addPost(post: CreatePostRequest): Post
}