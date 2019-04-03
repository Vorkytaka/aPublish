import com.fasterxml.jackson.databind.SerializationFeature
import data.DatabaseHelper
import data.mapper.Mapper
import data.repository.ApiRepository
import data.repository.IApiRepository
import data.table.Posts
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import model.Post
import org.jetbrains.exposed.sql.ResultRow
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

    val mapper: Mapper<ResultRow, Post> = {
        Post(
            this[Posts.id],
            this[Posts.author],
            this[Posts.content],
            this[Posts.createdDate]
        )
    }
    val repository: IApiRepository = ApiRepository(mapper)

    install(Routing) {
        api(repository)
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