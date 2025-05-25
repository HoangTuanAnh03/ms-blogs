package com.blog.gatewayserver.configration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import  com.blog.gatewayserver.dto.ApiResponse;
import  com.blog.gatewayserver.service.AuthService;
import  com.blog.gatewayserver.utils.CustomHeaders;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    AuthService authService;
    ObjectMapper objectMapper;

    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefix;

    @NonFinal
    private String[] publicEndpoints = {
            "/auth/.*",
            "/users/fetchUserByIdIn",
            "/auth/.*",
    };


//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("Enter authentication filter....");
//
////        System.out.println(exchange.getRequest().getURI().getPath());
////        System.out.println(isPublicEndpoint(exchange.getRequest()));
//        if (isPublicEndpoint(exchange.getRequest()))
//            return chain.filter(exchange);
//
//        // Get token from authorization header
//        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
//        if (CollectionUtils.isEmpty(authHeader))
//            return unauthenticated(exchange.getResponse());
//
//        String token = authHeader.get(0).replace("Bearer ", "");
//        log.info("Token: {}", token);
//
//        return authService.introspect(token).flatMap(introspectResponse -> {
//            if (introspectResponse.getData().isValid())
//                return chain.filter(
//                        exchange.mutate().request(
//                                exchange.getRequest().mutate()
//                                        .header(CustomHeaders.X_AUTH_USER_ID, introspectResponse.getData().getUid())
//                                        .header(CustomHeaders.X_AUTH_USER_AUTHORITIES, introspectResponse.getData().getAuthorities())
//                                        .build()
//                        ).build()
//                );
//            else
//                return unauthenticated(exchange.getResponse());
//        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
//    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();
        List<String> authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (!CollectionUtils.isEmpty(authHeader)) {
            token = authHeader.get(0).replace("Bearer ", "");
            log.info("Token: {}", token);
        }

        if (isPublicEndpoint(request)) {
            return chain.filter(exchange);
        }

        if (path.matches(apiPrefix + "/blog/admin(?:/.*)?")) {
            if (token == null) return unauthenticated(exchange.getResponse());
        }

        if (path.matches(apiPrefix + "/blog/.*") && method == HttpMethod.GET) {
            if (token != null) {
                return authService.introspect(token).flatMap(introspectResponse -> {
                    if (introspectResponse.getData().isValid()) {
                        return chain.filter(
                                exchange.mutate().request(
                                        request.mutate()
                                                .header(CustomHeaders.X_AUTH_USER_ID, introspectResponse.getData().getUid())
                                                .header(CustomHeaders.X_AUTH_USER_AUTHORITIES, introspectResponse.getData().getAuthorities())
                                                .build()
                                ).build()
                        );
                    } else {
                        return chain.filter(exchange);
                    }
                }).onErrorResume(throwable -> chain.filter(exchange));
            }
            return chain.filter(exchange);
        }

        if (token == null) {
            return unauthenticated(exchange.getResponse());
        }

        return authService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getData().isValid()) {
                log.info("token2: {}", introspectResponse.getData().getUid());
                return chain.filter(
                        exchange.mutate().request(
                                request.mutate()
                                        .header(CustomHeaders.X_AUTH_USER_ID, introspectResponse.getData().getUid())
                                        .header(CustomHeaders.X_AUTH_USER_AUTHORITIES, introspectResponse.getData().getAuthorities())
//                                        .header("Authorization", "Bearer " + token)
                                        .build()
                        ).build()
                );
            } else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }
    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("Unauthenticated1")
                .data(null)
                .build();

        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    private boolean isPublicEndpoint(ServerHttpRequest request){
        String path = request.getURI().getPath();
//        HttpMethod method = request.getMethod();
////        return Arrays.stream(publicEndpoints)
////                .anyMatch(s -> request.getURI().getPath().matches(apiPrefix + s));
//
//        if (path.matches(apiPrefix + "/blog/.*")) {
//            return method == HttpMethod.GET;
//        }

//        System.out.println(path);
        return Arrays.stream(publicEndpoints)
                .anyMatch(s -> path.matches(apiPrefix + s));
    }
}