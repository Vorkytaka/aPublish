package di

import data.mapper.Mapper
import data.mapper.dbMapper
import data.mapper.responseMapper
import data.repository.ApiRepository
import data.repository.IApiRepository
import data.response.PostResponse
import data.service.ApiService
import data.service.IApiService
import data.table.PostEntity
import model.Post
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<Mapper<PostEntity, Post>>(named("dbMapper")) { dbMapper }
    single<Mapper<Post, PostResponse>>(named("responseMapper")) { responseMapper }

    single<IApiRepository> { ApiRepository(get(named("dbMapper"))) }
    single<IApiService> { ApiService(get(), get(named("responseMapper"))) }
}