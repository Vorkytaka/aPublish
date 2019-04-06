import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseHelper
import di.appModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import routing.api
import service.IApiService

const val POST_ON_PAGE_COUNT = 10

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    DatabaseHelper.init()

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Koin) {
        modules(appModule)
    }

    val service: IApiService by inject()

    install(Routing) {
        api(service)
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