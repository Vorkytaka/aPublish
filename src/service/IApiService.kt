package service

import data.request.CreatePostRequest
import data.response.PageResponse
import data.response.PostResponse

interface IApiService {
    suspend fun getPage(page: Int): PageResponse
    suspend fun findPostById(id: Long): PostResponse?
    suspend fun addPost(post: CreatePostRequest): PostResponse
    suspend fun findPostsByAuthor(author: String, page: Int): PageResponse
    suspend fun findPostsByTag(tag: String, page: Int): PageResponse
}