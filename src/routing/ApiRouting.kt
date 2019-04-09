package routing

import data.request.CreatePostRequest
import data.response.PageResponse
import exception.ArgumentException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import service.IApiService

fun Route.api(service: IApiService) {
    route("api") {

        get("/{page?}") {
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.pageRespond(service.getPage(page))
        }

        get("/p/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

            val post = service.getPost(id)
            if (post != null) {
                call.respond(post)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/new") {
            val post = call.receive<CreatePostRequest>()
            call.respond(HttpStatusCode.Created, service.addPost(post))
        }

        get("/t/{theme}/{page?}") {
            val theme: String = call.parameters["theme"]
                ?: throw ArgumentException("theme")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.pageRespond(service.findPostsByTheme(theme, page))
        }

        get("/a/{author}/{page?}") {
            val author: String = call.parameters["author"]
                ?: throw ArgumentException("author")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.pageRespond(service.findPostsByAuthor(author, page))
        }
    }
}

private suspend inline fun ApplicationCall.pageRespond(page: PageResponse) {
    if (page.posts.isEmpty()) {
        this.respond(HttpStatusCode.NotFound)
    } else {
        this.respond(page)
    }
}