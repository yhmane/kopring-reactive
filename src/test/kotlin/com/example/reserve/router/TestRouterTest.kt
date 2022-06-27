package com.example.reserve.router

import com.example.reserve.handler.TestHandler
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration(
    classes = [
        TestHandler::class,
        TestRouter::class
    ]
)
class TestRouterTest(
    private val testRouter: TestRouter,
    private val testHandler: TestHandler,
) : DescribeSpec({

    val webClient: WebTestClient = WebTestClient
        .bindToRouterFunction(testRouter.router(testHandler))
        .build()

    describe("test router") {
        context("/test로 접근") {
            it("200 success") {
                webClient.get()
                    .uri(ENDPOINT)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody(String::class.java)
                    .value { value -> Assertions.assertEquals(value, "hello") }
            }
        }

        context("/invalid로 접근") {
            it("404 BadRequest") {
                webClient.get()
                    .uri(INVALID_ENDPOINT)
                    .exchange()
                    .expectStatus().is4xxClientError
            }
        }

    }
}) {
    companion object {
        private const val ENDPOINT = "/test"
        private const val INVALID_ENDPOINT = "/invalid"
    }
}
