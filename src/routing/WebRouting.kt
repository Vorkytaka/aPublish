package routing

import data.request.NewPostRequest
import data.response.PageResponse
import data.response.PostResponse
import exception.ArgumentException
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer
import service.IApiService

fun Route.web(service: IApiService) {
    route("/") {

        get("/{page?}") {
            val page = call.parameters["page"]?.toIntOrNull() ?: 0
            val pageResponse = service.getPage(page).mapToText()

            val prevPage = if (page != 0) "/${page - 1}" else null
            val nextPage = if (pageResponse.hasNextPage) "/${page + 1}" else null
            val data = mapOf(
                "page" to pageResponse,
                "prevPage" to prevPage,
                "nextPage" to nextPage
            )

            call.respond(
                FreeMarkerContent(
                    "main_page.ftl",
                    data
                )
            )
        }

        get("/author/{author}/{page?}") {
            val author = call.parameters["author"]
                ?: throw ArgumentException("author")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            val pageResponse = service.findPostsByAuthor(author, page)

            val prevPage = if (page != 0) "/author/$author/${page - 1}" else null
            val nextPage = if (pageResponse.hasNextPage) "/author/$author/${page + 1}" else null
            val data = mapOf(
                "page" to pageResponse,
                "prevPage" to prevPage,
                "nextPage" to nextPage,
                "query" to author
            )

            call.respond(
                FreeMarkerContent(
                    "search-page.ftl",
                    data
                )
            )
        }

        get("/tag/{tag}/{page?}") {
            val tag = call.parameters["tag"]
                ?: throw ArgumentException("tag")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            val pageResponse = service.findPostsByTag(tag, page)

            val prevPage = if (page != 0) "/tag/$tag/${page - 1}" else null
            val nextPage = if (pageResponse.hasNextPage) "/tag/$tag/${page + 1}" else null
            val data = mapOf(
                "page" to pageResponse,
                "prevPage" to prevPage,
                "nextPage" to nextPage,
                "query" to tag
            )

            call.respond(
                FreeMarkerContent(
                    "search-page.ftl",
                    data
                )
            )
        }

        get("/post/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

            val postResponse = service.findPostById(id)
            val response: Any = if (postResponse == null) {
                HttpStatusCode.NotFound
            } else {
                FreeMarkerContent(
                    "post.ftl",
                    mapOf("post" to postResponse.mapToHtml())
                )
            }

            call.respond(response)
        }

        get("/post") {
            call.respond(
                FreeMarkerContent(
                    "newPost.ftl",
                    null
                )
            )
        }

        post("/post") {
            val params = call.receive<Parameters>()

            val tags: Array<String> = with(params["tags"]) {
                if (this.isNullOrBlank()) {
                    emptyArray()
                } else {
                    this
                        .split(",")
                        .map(String::trim)
                        .toTypedArray()
                }
            }

            val lang = with(params["lang"]) {
                if (this.isNullOrBlank()) {
                    null
                } else {
                    this
                }
            }

            val post = NewPostRequest(
                params["author"],
                params["title"]!!,
                params["content"]!!,
                lang,
                tags
            )

            val createdPost = service.addPost(post)
            call.respondRedirect("/post/${createdPost.id}")
        }

        get("/about") {
            call.respond(
                FreeMarkerContent(
                    "about.ftl",
                    null
                )
            )
        }

    }
}

private val parser = Parser.builder().build()
private val textRenderer = TextContentRenderer.builder().stripNewlines(false).build()
private val htmlRenderer = HtmlRenderer.builder().build()

private fun PostResponse.mapToText() = PostResponse(
    this.id,
    this.author,
    this.title,
    textRenderer.render(parser.parse(this.content)),
    this.createdDate,
    this.lang,
    this.tags
)

private fun PostResponse.mapToHtml() = PostResponse(
    this.id,
    this.author,
    this.title,
    htmlRenderer.render(parser.parse(this.content)),
    this.createdDate,
    this.lang,
    this.tags
)

private fun PageResponse.mapToText() = PageResponse(
    this.page,
    this.posts.map { it.mapToText() },
    this.hasNextPage
)