package com.example.reserve.router

import com.example.reserve.handler.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class TestRouter {

    @Bean
    fun router(testHandler: TestHandler) = router {
        GET("/test", testHandler::test)
    }
}
