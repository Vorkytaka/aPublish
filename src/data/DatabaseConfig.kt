package data

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI

class DatabaseConfig private constructor(
    val driverClassName: String,
    val jdbcUrl: String,
    val maximumPoolSize: Int,
    val isAutoCommit: Boolean,
    val transactionIsolation: String,
    val username: String?,
    val password: String?
) {
    class Builder {
        var driverClassName: String = "org.h2.Driver"
        var jdbcUrl: String = "jdbc:h2:mem:test"
        var maximumPoolSize: Int = 3
        var isAutoCommit: Boolean = false
        var transactionIsolation: String = "TRANSACTION_REPEATABLE_READ"
        var username: String? = null
        var password: String? = null

        fun build() = DatabaseConfig(
            driverClassName,
            jdbcUrl,
            maximumPoolSize,
            isAutoCommit,
            transactionIsolation,
            username,
            password
        )
    }

    companion object {
        @JvmStatic
        private fun create(init: Builder.() -> Unit): DatabaseConfig {
            return Builder().apply(init).build()
        }

        @KtorExperimentalAPI
        @JvmStatic
        fun fromServerConfig(serverConfig: ApplicationConfig): DatabaseConfig {
            return create {
                driverClassName = serverConfig.property("db.config.driverClassName").getString()
                jdbcUrl = serverConfig.property("db.config.jdbcUrl").getString()
                maximumPoolSize = serverConfig.property("db.config.maximumPoolSize").getString().toInt()
                isAutoCommit = serverConfig.property("db.config.isAutoCommit").getString().toBoolean()
                transactionIsolation = serverConfig.property("db.config.transactionIsolation").getString()

                username = serverConfig.propertyOrNull("db.credentials.username")?.getString()
                password = serverConfig.propertyOrNull("db.credentials.password")?.getString()
            }
        }

        @JvmStatic
        fun fromEnvironment(args: Map<String, String>): DatabaseConfig {
            return create {
                driverClassName = args["db.config.driverClassName"]!!
                jdbcUrl = args["db.config.jdbcUrl"]!!
                maximumPoolSize = args["db.config.maximumPoolSize"]!!.toInt()
                isAutoCommit = args["db.config.isAutoCommit"]!!.toBoolean()
                transactionIsolation = args["db.config.transactionIsolation"]!!

                username = args["db.credentials.username"]
                password = args["db.credentials.password"]
            }
        }
    }
}