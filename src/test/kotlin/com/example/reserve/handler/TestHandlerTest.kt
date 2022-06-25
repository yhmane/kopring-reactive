package com.example.reserve.handler

import com.example.reserve.router.TestRouter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    classes = [
        TestHandler::class,
        TestRouter::class,
    ]
)
class TestHandlerTest {

    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var routeConfig: TestRouter

    @Autowired
    private lateinit var testHandler: TestHandler

    @BeforeEach
    fun beforeTest() {
        client = WebTestClient
            .bindToRouterFunction(routeConfig.router(testHandler))
            .build()
    }

    @Test
    fun handlerTest() {
        client.get()
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { value -> assertEquals(value, "hello") }
    }

    @Test
    fun notFoundHandlerMappingTest() {
        client.get()
            .uri(INVALID_ENDPOINT)
            .exchange()
            .expectStatus().is4xxClientError
    }

    companion object {
        private const val ENDPOINT = "/test"
        private const val INVALID_ENDPOINT = "/invalid"
    }
}
