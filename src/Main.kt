import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseHelper
import data.repository.ApiRepository
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import routing.api

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    DatabaseHelper.init()

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Routing) {
        api(ApiRepository())
    }
}

fun main(args: Array<String>) {
    embeddedServer(
        factory = Netty,
        port = 8080,
        watchPaths = listOf("MainKt"),
        module = Application::module
    ).start()
}