package data.repository

import model.NewPost
import model.Post

interface IApiRepository {
    suspend fun getAllPosts(): List<Post>
    suspend fun getPost(id: Long): Post?
    suspend fun addPost(post: NewPost): Post
}