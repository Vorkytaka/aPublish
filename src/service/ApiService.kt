package service

import POST_ON_PAGE_COUNT
import data.mapper.Mapper
import data.repository.IApiRepository
import data.request.NewPostRequest
import data.response.PageResponse
import data.response.PostResponse
import data.validation.Validator
import model.Post

class ApiService(
    private val repository: IApiRepository,
    private val mapper: Mapper<Post, PostResponse>,
    private val validator: Validator<NewPostRequest>

) : IApiService {
    override suspend fun getPage(page: Int): PageResponse {
        val posts = repository.getPage(page)
        return PageResponse(
            page,
            posts.take(POST_ON_PAGE_COUNT).map(mapper),
            posts.size > POST_ON_PAGE_COUNT
        )
    }

    override suspend fun findPostById(id: Long): PostResponse? {
        val post = repository.findPostById(id)
        return if (post == null) null else mapper(post)
    }

    override suspend fun addPost(post: NewPostRequest): PostResponse {
        post.validator()
        return mapper(repository.addPost(post))
    }

    override suspend fun findPostsByAuthor(author: String, page: Int): PageResponse {
        val posts = repository.findPostsByAuthor(author, page)
        return PageResponse(
            page,
            posts.take(POST_ON_PAGE_COUNT).map(mapper),
            posts.size > POST_ON_PAGE_COUNT
        )
    }

    override suspend fun findPostsByTag(tag: String, page: Int): PageResponse {
        val posts = repository.findPostsByTag(tag, page)
        return PageResponse(
            page,
            posts.take(POST_ON_PAGE_COUNT).map(mapper),
            posts.size > POST_ON_PAGE_COUNT
        )
    }
}