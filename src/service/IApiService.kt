package service

import data.request.CreatePostRequest
import data.response.PageResponse
import data.response.PostResponse

interface IApiService {
    suspend fun getPage(page: Int): PageResponse
    suspend fun getPost(id: Long): PostResponse?
    suspend fun addPost(post: CreatePostRequest): PostResponse
    suspend fun findPostsByTheme(theme: String, page: Int): PageResponse
    suspend fun findPostsByAuthor(author: String, page: Int): PageResponse
}