package routing

import data.repository.IApiRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import model.NewPost

fun Route.api(repository: IApiRepository) {
    route("api") {
        get("/") {
            call.respond(repository.getAllPosts())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLong()!!
            val message = repository.getPost(id)

            if (message != null) {
                call.respond(message)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/new") {
            val post = call.receive<NewPost>()
            call.respond(HttpStatusCode.Created, repository.addPost(post))
        }
    }
}