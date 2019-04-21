package routing

import data.request.CreatePostRequest
import exception.ArgumentException
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import service.IWebService

fun Route.web(service: IWebService) {
    route("/") {

        get("/{page?}") {
            val page = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.getPage(page))
        }

        get("/post/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

            call.respond(service.findPostById(id))
        }

        get("/post") {
            call.respond(service.newPostPage())
        }

        post("/post/") {
            val params = call.receive<Parameters>()

            // todo: validate params

            val tagsString = params["tags"]

            val tags: Array<String> = if (tagsString.isNullOrBlank()) {
                emptyArray()
            } else {
                tagsString
                    .split(",")
                    .map(String::trim)
                    .toTypedArray()
            }

            val post = CreatePostRequest(
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