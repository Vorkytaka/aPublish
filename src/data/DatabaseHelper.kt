package data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import data.table.Posts
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

@KtorExperimentalAPI
object DatabaseHelper {
    fun init(config: DatabaseConfig) {
        Database.connect(createDataSource(config))
        transaction {
            SchemaUtils.create(Posts)
        }
    }

    private fun createDataSource(config: DatabaseConfig): DataSource {
        val hikariConfig = HikariConfig().apply {

            driverClassName = config.driverClassName
            jdbcUrl = config.jdbcUrl
            maximumPoolSize = config.maximumPoolSize
            isAutoCommit = config.isAutoCommit
            transactionIsolation = config.transactionIsolation

            username = config.username
            password = config.password

            validate()
        }
        return HikariDataSource(hikariConfig)
    }
}