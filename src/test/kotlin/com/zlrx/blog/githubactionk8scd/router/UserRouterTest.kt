package com.zlrx.blog.githubactionk8scd.router

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class UserRouterTest {

    private val userService = UserService()

    @Test
    fun `user service should return with two users`() {
        val result = runBlocking {
            userService.getUsers().toList()
        }
        Assertions.assertEquals(2, result.size)
    }

}