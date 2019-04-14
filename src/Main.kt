import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseConfig
import data.DatabaseHelper
import di.appModule
import exception.ArgumentException
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
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
import routing.web
import service.IApiService
import service.IWebService

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
            configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
        }
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Koin) {
        modules(appModule)
    }

    install(StatusPages) {
        exception<ArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    install(Routing) {
        api(inject<IApiService>().value)
        web(inject<IWebService>().value)
    }
}

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        commandLineEnvironment(args)
    ).start()
}