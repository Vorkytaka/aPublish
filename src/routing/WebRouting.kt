package routing

import data.request.NewPostRequest
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
import service.IApiService

fun Route.web(service: IApiService) {
    route("/") {

        get("/{page?}") {
            val page = call.parameters["page"]?.toIntOrNull() ?: 0
            val pageResponse = service.getPage(page)
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
                    mapOf("post" to postResponse)
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

    }
}