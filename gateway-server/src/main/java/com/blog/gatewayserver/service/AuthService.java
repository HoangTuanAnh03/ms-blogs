package com.blog.gatewayserver.service;

import  com.blog.gatewayserver.dto.ApiResponse;
import  com.blog.gatewayserver.dto.request.IntrospectRequest;
import  com.blog.gatewayserver.dto.response.IntrospectResponse;
import  com.blog.gatewayserver.service.client.AuthClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AuthClient authClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return authClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}