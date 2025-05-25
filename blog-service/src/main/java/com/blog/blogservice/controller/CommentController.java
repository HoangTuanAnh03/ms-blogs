package com.blog.blogservice.controller;


import com.blog.blogservice.dto.ApiResponse;
import com.blog.blogservice.dto.request.CommentRequest;
import com.blog.blogservice.dto.response.CommentResponse;
import com.blog.blogservice.entity.Comment;
import com.blog.blogservice.service.impl.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/comment")
@AllArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<Comment>> comment(@RequestBody CommentRequest commentRequest,
														@RequestHeader(value = "X-Auth-User-Id", required = true) String uid){

		Comment comment = commentService.addComment(commentRequest.getParentId(), commentRequest.getContent(), uid, commentRequest.getPid());
		ApiResponse<Comment> apiResponse = ApiResponse.<Comment>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(comment)
				.build();

		return ResponseEntity.ok()
				.body(apiResponse);
	}

	@GetMapping("/{pid}")
	public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable String pid){

		ApiResponse<List<CommentResponse>> apiResponse = ApiResponse.<List<CommentResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(commentService.getComments(pid))
				.build();

		return ResponseEntity.ok()
				.body(apiResponse);
	}
}
