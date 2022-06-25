package com.example.reserve.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class TestHandler {

    fun test(serverRequest: ServerRequest) : Mono<ServerResponse> = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just("hello"), String::class.java)
        .switchIfEmpty(notFound().build())
}