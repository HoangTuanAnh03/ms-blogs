package com.blog.gatewayserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {
	@Value("${app.api-prefix}")
	String apiPrefix;

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator RouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path(apiPrefix + "/auth/**", apiPrefix + "/users/**")
						.filters(f -> f
								.rewritePath(apiPrefix + "/auth/(?<segment>.*)", "/auth/${segment}")
								.rewritePath(apiPrefix + "/users/(?<segment>.*)", "/users/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://AUTH-SERVICE"))
				.route(p -> p
						.path(apiPrefix + "/blog/**")
						.filters(f -> f
								.rewritePath(apiPrefix + "/blog/(?<segment>.*)", "/blog/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://BLOG-SERVICE"))
//						.uri("http://localhost:8090")
				.build();
	}
}
