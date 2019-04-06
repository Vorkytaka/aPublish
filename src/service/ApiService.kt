package service

import POST_ON_PAGE_COUNT
import data.mapper.Mapper
import data.repository.IApiRepository
import data.request.CreatePostRequest
import data.response.PageResponse
import data.response.PostResponse
import model.Post

class ApiService(
    private val repository: IApiRepository,
    private val mapper: Mapper<Post, PostResponse>
) : IApiService {
    override suspend fun getPage(page: Int): PageResponse {
        val posts = repository.getPage(page)
        return PageResponse(
            page,
            posts.take(10).map(mapper),
            posts.size > POST_ON_PAGE_COUNT
        )
    }

    override suspend fun getPost(id: Long): PostResponse? {
        val post = repository.getPost(id)
        return if (post == null) null else mapper(post)
    }

    override suspend fun addPost(post: CreatePostRequest): PostResponse {
        return mapper(repository.addPost(post))
    }
}