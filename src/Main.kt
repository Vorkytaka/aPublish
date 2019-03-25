import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
}

fun main(args: Array<String>) {
    embeddedServer(
        factory = Netty,
        port = 8080,
        watchPaths = listOf("MainKt"),
        module = Application::module
    ).start()
}