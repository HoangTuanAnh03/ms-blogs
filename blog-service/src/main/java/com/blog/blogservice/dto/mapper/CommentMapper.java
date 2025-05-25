package com.blog.blogservice.dto.mapper;

import com.blog.blogservice.dto.response.CommentResponse;
import com.blog.blogservice.dto.response.SimpInfoUserResponse;
import com.blog.blogservice.entity.Comment;
import com.blog.blogservice.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentMapper {
//	AuthClient authClient;
	IAuthService authService;
	public List<CommentResponse> buildTreeFromNestedSet(List<CommentResponse> flatList) {

		Set<String> userIds = flatList.stream()
				.map(CommentResponse::getUserId)
				.collect(Collectors.toSet());

//		System.out.println(userIds.toString());

		Map<String, SimpInfoUserResponse> userMap = authService.getUserByIds(new ArrayList<>(userIds)).stream()
				.collect(Collectors.toMap(SimpInfoUserResponse::getId, Function.identity()));
//		Map<String, SimpInfoUserResponse> userMap = new HashMap<>();
		for (CommentResponse comment : flatList) {
			comment.setUserResponse(userMap.get(comment.getUserId()));
		}

		List<CommentResponse> mutableList = new ArrayList<>(flatList);
		mutableList.sort(Comparator.comparingInt(CommentResponse::getLeftValue));
		Deque<CommentResponse> stack = new ArrayDeque<>();
		List<CommentResponse> roots = new ArrayList<>();
		for (CommentResponse comment : flatList) {
			while (!stack.isEmpty() && stack.peek().getRightValue() < comment.getLeftValue()) {
				stack.pop();
			}
			if (!stack.isEmpty()) {
				stack.peek().getReplies().add(comment);
			} else {
				roots.add(comment);
			}
			stack.push(comment);
		}
		return roots;
	}

	public CommentResponse toCommentResponse(Comment comment){
		return CommentResponse.builder()
				.id(comment.getId())
				.replies(new ArrayList<>())
				.userId(comment.getUid())
				.rightValue(comment.getRightValue())
				.leftValue(comment.getLeftValue())
				.content(comment.getContent())
				.createdAt(comment.getCreatedAt())
				.build();
	}
}
