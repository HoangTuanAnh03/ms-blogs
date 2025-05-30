package com.blog.blogservice.dto.mapper;

import com.blog.blogservice.dto.response.NotificationResponse;
import com.blog.blogservice.entity.Notification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationMapper {
	public NotificationResponse toDto(Notification noti) {
		return NotificationResponse.builder()
				.id(noti.getId())
				.uid(noti.getUid())
				.message(noti.getMessage())
				.isRead(noti.isRead())
				.postId(noti.getPost() != null ? noti.getPost().getId() : null)
				.postTitle(noti.getPost() != null ? noti.getPost().getTitle() : null)
				.build();
	}
}
