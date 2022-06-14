package com.zlrx.blog.githubactionk8scd.router

import com.zlrx.blog.githubactionk8scd.model.User
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList

@WebFluxTest
class UserRouterTest {

    private val client: WebTestClient = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8080")
        .build()

    @Test
    fun `user endpoint should return with two users`() {
        client.get()
            .uri("/api/v1/user")
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList<User>()
            .hasSize(2)
            .contains(User(1, "zlaval"), User(2, "zalerix"))
    }

}