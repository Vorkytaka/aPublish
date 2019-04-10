package data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import data.table.Posts
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

@KtorExperimentalAPI
object DatabaseHelper {
    fun init(serverConfig: ApplicationConfig) {
        Database.connect(createDataSource(serverConfig))
        transaction {
            SchemaUtils.create(Posts)
        }
    }

    private fun createDataSource(serverConfig: ApplicationConfig): DataSource {
        val config = HikariConfig().apply {

            driverClassName = serverConfig.property("db.config.driverClassName").getString()
            jdbcUrl = serverConfig.property("db.config.jdbcUrl").getString()
            maximumPoolSize = serverConfig.property("db.config.maximumPoolSize").getString().toInt()
            isAutoCommit = serverConfig.property("db.config.isAutoCommit").getString().toBoolean()
            transactionIsolation = serverConfig.property("db.config.transactionIsolation").getString()

            username = serverConfig.propertyOrNull("db.credentials.username")?.getString()
            password = serverConfig.propertyOrNull("db.credentials.password")?.getString()

            validate()
        }
        return HikariDataSource(config)
    }
}