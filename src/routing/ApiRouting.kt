package routing

import data.request.CreatePostRequest
import exception.ArgumentException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import service.IApiService

fun Route.api(service: IApiService) {
    route("api") {
        get {
            call.redirectToFirst()
        }

        get("/{page}") {
            val page: Int = call.parameters["page"]?.toIntOrNull()
                ?: throw ArgumentException("page")
            call.respond(service.getPage(page))
        }

        get("/p/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

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
            call.redirectToFirst()
        }

        get("/t/{theme}/{page}") {
            val theme: String = call.parameters["theme"]
                ?: throw ArgumentException("theme")
            val page: Int = call.parameters["page"]?.toIntOrNull()
                ?: throw ArgumentException("page")
            call.respond(service.findPostsByTheme(theme, page))
        }

        get("/a/{author}") {
            call.redirectToFirst()
        }

        get("/a/{author}/{page}") {
            val author: String = call.parameters["author"]
                ?: throw ArgumentException("authro")
            val page: Int = call.parameters["page"]?.toIntOrNull()
                ?: throw ArgumentException("page")
            call.respond(service.findPostsByAuthor(author, page))
        }
    }
}

private suspend inline fun ApplicationCall.redirectToFirst() {
    val path = this.request.path()
    val s = '/' == path.last()

    val newPath = StringBuilder().apply {
        append(path)
        if (!s) append('/')
        append('0')
    }.toString()

    this.respondRedirect(newPath)
}