package service

import data.mapper.Mapper
import data.repository.IApiRepository
import data.response.PostResponse
import model.NewPost
import model.Post

class ApiService(
    private val repository: IApiRepository,
    private val mapper: Mapper<Post, PostResponse>
) : IApiService {
    override suspend fun getPage(page: Int): List<PostResponse> {
        return repository.getPage(page).map(mapper)
    }

    override suspend fun getPost(id: Long): PostResponse? {
        val post = repository.getPost(id)
        return if (post == null) null else mapper(post)
    }

    override suspend fun addPost(post: NewPost): PostResponse {
        return mapper(repository.addPost(post))
    }
}