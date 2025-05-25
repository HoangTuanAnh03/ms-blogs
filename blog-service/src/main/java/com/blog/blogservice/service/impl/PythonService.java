package com.blog.blogservice.service.impl;

import com.blog.blogservice.dto.request.FilterRequest;
import com.blog.blogservice.dto.response.FilterResponse;
import com.blog.blogservice.service.IPythonService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PythonService implements IPythonService {
	@Override
	public FilterResponse filterContent(String content){
//		WebClient client = WebClient.create("http://localhost:1992/api/v1");
		WebClient client = WebClient.builder()
				.baseUrl("http://localhost:1992/api/v1")
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer
								.defaultCodecs()
								.maxInMemorySize(10 * 1024 * 1024))
						.build())
				.build();
		return client.post()
				.uri("/filter")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new FilterRequest(content))
				.retrieve()
				.bodyToMono(FilterResponse.class)
				.block();
	}

	@Override
	public String summaryContent(String content) {
		WebClient client = WebClient.create("http://localhost:1992/api/v1");
		return client.post()
				.uri("/summary")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new FilterRequest(content))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
}
