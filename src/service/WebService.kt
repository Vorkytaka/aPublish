package service

import io.ktor.freemarker.FreeMarkerContent

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
}