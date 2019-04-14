package service

interface IWebService {
    suspend fun getPage(page: Int): Any
}