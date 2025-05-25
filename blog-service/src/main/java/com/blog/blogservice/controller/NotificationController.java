package com.blog.blogservice.controller;

import com.blog.blogservice.advice.exception.BadRequestException;
import com.blog.blogservice.dto.ApiResponse;
import com.blog.blogservice.dto.response.NotificationResponse;
import com.blog.blogservice.entity.Notification;
import com.blog.blogservice.service.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/notification")
@AllArgsConstructor
public class NotificationController {
	INotificationService notificationService;

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotification(@RequestHeader(value = "X-Auth-User-Id", required = false) String uid){
		if(uid == null){
			throw new BadRequestException("UID not found");
		}

		ApiResponse<List<NotificationResponse>> apiResponse = ApiResponse.<List<NotificationResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Success")
				.data(notificationService.getNotification(uid))
				.build();
		return ResponseEntity.ok()
				.body(apiResponse);
	}
}
