package data.service

import data.response.PostResponse
import model.NewPost

interface IApiService {
    suspend fun getPage(page: Int): List<PostResponse>
    suspend fun getPost(id: Long): PostResponse?
    suspend fun addPost(post: NewPost): PostResponse
}