package routing

import data.request.CreatePostRequest
import exception.ArgumentException
import io.ktor.application.call
import io.ktor.http.CacheControl
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.cacheControl
import io.ktor.response.respond
import io.ktor.routing.*
import service.IApiService

fun Route.api(service: IApiService) {
    route("api") {

        get("/{page?}") {
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.getPage(page))
        }

        get("/post/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

            val post = service.findPostById(id)
            if (post != null) {
                val cacheControl = CacheControl.MaxAge(31536000, visibility = CacheControl.Visibility.Public)
                call.response.cacheControl(cacheControl)
                call.respond(post)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        head("/post/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw ArgumentException("id")

            val post = service.findPostById(id)
            if (post != null) {
                val cacheControl = CacheControl.MaxAge(31536000, visibility = CacheControl.Visibility.Public)
                call.response.cacheControl(cacheControl)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/post") {
            val post = call.receive<CreatePostRequest>()
            val message = service.addPost(post)
            call.response.headers.append("Location", "/post/${message.id}")
            call.respond(HttpStatusCode.Created)
        }

        get("/author/{author}/{page?}") {
            val author: String = call.parameters["author"]
                ?: throw ArgumentException("author")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.findPostsByAuthor(author, page))
        }

        get("/tag/{tag}/{page?}") {
            val tag: String = call.parameters["tag"]
                ?: throw ArgumentException("tag")
            val page: Int = call.parameters["page"]?.toIntOrNull() ?: 0
            call.respond(service.findPostsByTag(tag, page))
        }
    }
}
