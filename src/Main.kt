import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseConfig
import data.DatabaseHelper
import di.appModule
import exception.ArgumentException
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import routing.api
import service.IApiService

const val POST_ON_PAGE_COUNT = 10

@KtorExperimentalAPI
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    val databaseConfig = DatabaseConfig.fromServerConfig(this.environment.config)
    DatabaseHelper.init(databaseConfig)

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Koin) {
        modules(appModule)
    }

    install(StatusPages) {
        exception<ArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    val service: IApiService by inject()

    install(Routing) {
        api(service)
    }
}

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        commandLineEnvironment(args)
    ).start()
}