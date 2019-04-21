package di

import data.mapper.Mapper
import data.mapper.mapToPost
import data.mapper.mapToPostResponse
import data.repository.ApiRepository
import data.repository.IApiRepository
import data.response.PostResponse
import data.table.PostEntity
import model.Post
import org.koin.core.qualifier.named
import org.koin.dsl.module
import service.ApiService
import service.IApiService
import service.IWebService
import service.WebService

val appModule = module {
    single<Mapper<PostEntity, Post>>(named("dbMapper")) { PostEntity::mapToPost }
    single<Mapper<Post, PostResponse>>(named("responseMapper")) { Post::mapToPostResponse }

    single<IApiRepository> { ApiRepository(get(named("dbMapper"))) }
    single<IApiService> { ApiService(get(), get(named("responseMapper"))) }

    single<IWebService> { WebService(get()) }
}