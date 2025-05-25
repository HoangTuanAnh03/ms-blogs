package com.blog.blogservice.controller;

import com.blog.blogservice.advice.exception.BadRequestException;
import com.blog.blogservice.dto.ApiResponse;
import com.blog.blogservice.dto.record.CategoryPostCountDTO;
import com.blog.blogservice.dto.response.CategoryResponse;
import com.blog.blogservice.dto.response.PostResponse;
import com.blog.blogservice.service.IBlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/blog/admin")
@AllArgsConstructor
public class AdminController {
	IBlogService blogService;
	@GetMapping("/dashboard")
	public ResponseEntity<ApiResponse<Map<String, Object>>> dashboard(
			@RequestHeader(value = "X-Auth-User-Id", required = false, defaultValue = "") String uid,
			@RequestHeader(value = "X-Auth-User-Authorities", required = false, defaultValue = "") String role
	){
		if(!Objects.equals(role, "ROLE_ADMIN")){
			throw new BadRequestException("unauthorized");
		}
		Map<String, Object> res = new HashMap<>();
		res.put("pie_chart", blogService.postStas());
		res.put("bar_chart", blogService.getPostCountByMonthInLastSixMonths());
		res.put("table", blogService.getCategoryPostStas());
		ApiResponse<Map<String, Object>> apiResponse = ApiResponse.<Map<String, Object>>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(res)
				.build();
		return ResponseEntity.ok()
				.body(apiResponse);
	}

	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<CategoryPostCountDTO>>> category(
			@RequestHeader(value = "X-Auth-User-Id", required = false, defaultValue = "") String uid,
			@RequestHeader(value = "X-Auth-User-Authorities", required = false, defaultValue = "") String role
	){
		if(!Objects.equals(role, "ROLE_ADMIN")){
			throw new BadRequestException("unauthorized");
		}
		ApiResponse<List<CategoryPostCountDTO>> apiResponse = ApiResponse.<List<CategoryPostCountDTO>>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(blogService.getCategoryPostStas())
				.build();
		return ResponseEntity.ok()
				.body(apiResponse);
	}
}
