package service

import data.response.PageResponse
import data.response.PostResponse
import model.NewPost

interface IApiService {
    suspend fun getPage(page: Int): PageResponse
    suspend fun getPost(id: Long): PostResponse?
    suspend fun addPost(post: NewPost): PostResponse
}