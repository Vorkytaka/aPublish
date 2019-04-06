package routing

import data.request.CreatePostRequest
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
        get {
            call.respond(service.getPage(0))
        }

        get("/{page}") {
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.getPage(page))
        }

        get("/p/{id}") {
            val id = call.parameters["id"]?.toLong()!!
            val message = service.getPost(id)

            if (message != null) {
                call.respond(message)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/new") {
            val post = call.receive<CreatePostRequest>()
            call.respond(HttpStatusCode.Created, service.addPost(post))
        }

        get("/t/{theme}") {
            val theme: String = call.parameters["theme"] ?: return@get // todo: http response
            call.respond(service.findPostsByTheme(theme, 0))
        }

        get("/t/{theme}/{page}") {
            val theme: String = call.parameters["theme"] ?: return@get // todo: http response
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.findPostsByTheme(theme, page))
        }

        get("/a/{author}") {
            val author: String = call.parameters["author"] ?: return@get // todo: http response
            call.respond(service.findPostsByAuthor(author, 0))
        }

        get("/a/{author}/{page}") {
            val author: String = call.parameters["author"] ?: return@get // todo: http response
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.findPostsByAuthor(author, page))
        }
    }
}