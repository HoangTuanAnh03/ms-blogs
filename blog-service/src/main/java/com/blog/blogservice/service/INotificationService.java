package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.NotificationResponse;
import com.blog.blogservice.entity.Notification;

import java.util.List;

public interface INotificationService {
	public List<NotificationResponse> getNotification(String uid);
}
