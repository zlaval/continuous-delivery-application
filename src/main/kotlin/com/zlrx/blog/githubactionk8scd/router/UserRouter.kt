package com.zlrx.blog.githubactionk8scd.router

import com.zlrx.blog.githubactionk8scd.model.User
import kotlinx.coroutines.flow.flow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter {

    @Bean
    fun routerFn() = coRouter {
        "/api/v1".nest {
            GET("user") {
                val response = flow {
                    emit(User(1, "zlaval"))
                    emit(User(2, "zalerix"))
                }
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyAndAwait(response)
            }
        }
    }

}