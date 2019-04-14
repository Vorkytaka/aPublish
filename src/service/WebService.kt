package service

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class WebService(
    private val apiService: IApiService
) : IWebService {
    override suspend fun getPage(page: Int): Any {
        val pageResponse = apiService.getPage(page)
        return FreeMarkerContent(
            "page.ftl",
            mapOf("page" to pageResponse)
        )
    }

    override suspend fun findPostById(id: Long): Any {
        val postResponse = apiService.findPostById(id)
        return if (postResponse == null) {
            HttpStatusCode.NotFound
        } else {
            FreeMarkerContent(
                "post.ftl",
                mapOf("post" to postResponse)
            )
        }
    }
}