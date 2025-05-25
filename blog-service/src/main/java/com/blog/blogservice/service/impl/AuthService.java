package com.blog.blogservice.service.impl;

import com.blog.blogservice.dto.ApiResponse;
import com.blog.blogservice.dto.response.SimpInfoUserResponse;
import com.blog.blogservice.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {
	private String AUTH_URL = "http://localhost:8080/users";
	public List<SimpInfoUserResponse> getUserByIds(List<String> ids){
		if(ids.isEmpty()) {
			return Collections.emptyList();
		}
		String queryParams = ids.stream()
				.map(id -> "ids=" + URLEncoder.encode(id, StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));

		WebClient client = WebClient.create(AUTH_URL);
		ApiResponse<List<SimpInfoUserResponse>> response = client.get()
				.uri("/fetchUserByIdIn?" + queryParams)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ApiResponse<List<SimpInfoUserResponse>>>() {})
				.block();

		return response != null ? response.getData() : Collections.emptyList();
	}
}
