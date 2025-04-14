package com.blog.gatewayserver.service.client;

import  com.blog.gatewayserver.dto.ApiResponse;
import  com.blog.gatewayserver.dto.request.IntrospectRequest;
import  com.blog.gatewayserver.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthClient {
    @PostExchange(url = "/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}