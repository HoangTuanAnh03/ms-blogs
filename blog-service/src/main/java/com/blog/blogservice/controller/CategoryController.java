package com.blog.blogservice.controller;

import com.blog.blogservice.dto.ApiResponse;
import com.blog.blogservice.dto.response.CategoryResponse;
import com.blog.blogservice.entity.Comment;
import com.blog.blogservice.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/category")
@AllArgsConstructor
public class CategoryController {
	ICategoryService categoryService;
	@GetMapping("")
	public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll(){
		ApiResponse<List<CategoryResponse>> apiResponse = ApiResponse.<List<CategoryResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(categoryService.getAll())
				.build();

		return ResponseEntity.ok()
				.body(apiResponse);
	}
}
