package service

import data.request.NewPostRequest
import data.response.PostResponse

interface IWebService {
    suspend fun getPage(page: Int): Any
    suspend fun findPostById(id: Long): Any
    suspend fun newPostPage(): Any
    suspend fun addPost(post: NewPostRequest): PostResponse
}