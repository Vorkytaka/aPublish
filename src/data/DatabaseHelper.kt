package data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import data.table.PostEntity
import data.table.PostTags
import data.table.Posts
import data.table.Tags
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.sql.DataSource

@KtorExperimentalAPI
object DatabaseHelper {
    fun init(config: DatabaseConfig) {
        Database.connect(createDataSource(config))
        transaction {
            SchemaUtils.create(Posts)
            SchemaUtils.create(Tags)
            SchemaUtils.create(PostTags)

            transaction {
                transaction {
                    PostEntity.new {
                        title = "About"
                        content = """aPublish - it's an public, anonymous, immutable feed aggregator.

                            ### Links
                            [Source code](https://github.com/Vorkytaka/aPublish)""".trimIndent()
                        createdDate = DateTime(0L)
                        language = "en"
                    }
                }
            }
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