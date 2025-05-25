package com.blog.blogservice.service.impl;

import com.blog.blogservice.dto.mapper.NotificationMapper;
import com.blog.blogservice.dto.response.NotificationResponse;
import com.blog.blogservice.entity.Notification;
import com.blog.blogservice.repository.NotificationRepository;
import com.blog.blogservice.service.INotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService implements INotificationService {
	NotificationRepository notificationRepository;
	NotificationMapper notificationMapper;
	@Override
	public List<NotificationResponse> getNotification(String uid) {
		return notificationRepository.findAllByUidOrderByCreatedAtDesc(uid).stream().map(notificationMapper::toDto).toList();
	}
}
