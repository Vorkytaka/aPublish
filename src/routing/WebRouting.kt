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
            call.respond(
                FreeMarkerContent(
                    "page.ftl",
                    mapOf("page" to pageResponse)
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

            val post = NewPostRequest(
                params["author"],
                params["title"]!!,
                params["content"]!!,
                params["lang"],
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