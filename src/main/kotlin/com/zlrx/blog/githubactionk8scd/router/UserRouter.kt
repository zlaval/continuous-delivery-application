package com.zlrx.blog.githubactionk8scd.router

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter(
    private val userService: UserService
) {

    @Bean
    fun routerFn() = coRouter {
        "/api/v1".nest {
            GET("user") {
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyAndAwait(userService.getUsers())
            }
        }
    }
}

data class User(
    val id: Long,
    val name: String
)

@Service
class UserService {

    fun getUsers(): Flow<User> = flow {
        emit(User(1, "zlaval"))
        emit(User(2, "zalerix-modify"))
    }
}