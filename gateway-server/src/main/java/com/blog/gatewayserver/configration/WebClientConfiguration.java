package com.blog.gatewayserver.configration;

import  com.blog.gatewayserver.service.client.AuthClient;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfiguration {
    @Value("${app.url.auth}")
    @NonFinal
    private String urlAuth;

    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl(urlAuth)
                .build();
    }

    @Bean
    AuthClient authClient(WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(AuthClient.class);
    }
}
