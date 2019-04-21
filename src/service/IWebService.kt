package service

import data.request.CreatePostRequest
import data.response.PostResponse

interface IWebService {
    suspend fun getPage(page: Int): Any
    suspend fun findPostById(id: Long): Any
    suspend fun newPostPage(): Any
    suspend fun addPost(post: CreatePostRequest): PostResponse
}