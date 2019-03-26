package routing

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.NewPost
import service.IApiService

fun Route.api(service: IApiService) {
    route("api") {
        get("/") {
            call.respond(service.getAllPosts())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLong()!!
            val message = service.getPost(id)

            if (message != null) {
                call.respond(message)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/new") {
            val post = call.receive<NewPost>()
            call.respond(HttpStatusCode.Created, service.addPost(post))
        }
    }
}