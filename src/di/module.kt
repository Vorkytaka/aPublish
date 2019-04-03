package di

import data.mapper.Mapper
import data.mapper.dbMapper
import data.mapper.responseMapper
import data.repository.ApiRepository
import data.repository.IApiRepository
import data.response.PostResponse
import data.service.ApiService
import data.service.IApiService
import model.Post
import org.jetbrains.exposed.sql.ResultRow
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<Mapper<ResultRow, Post>>(named("dbMapper")) { dbMapper }
    single<Mapper<Post, PostResponse>>(named("responseMapper")) { responseMapper }

    single<IApiRepository> { ApiRepository(get(named("dbMapper"))) }
    single<IApiService> { ApiService(get(), get(named("responseMapper"))) }
}