package routing

import exception.ArgumentException
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
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

    }
}