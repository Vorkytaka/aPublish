package service

interface IWebService {
    suspend fun getPage(page: Int): Any
    suspend fun findPostById(id: Long): Any
}