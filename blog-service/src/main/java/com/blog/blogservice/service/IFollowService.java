package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.SimpInfoUserResponse;
import com.blog.blogservice.entity.Follow;

import java.util.List;

public interface IFollowService {
	Long countFollowByFollowerId(String followerId);
	Long countFollowByFollowingId(String followingId);

	Follow follow(String followerId, String followingId);

	boolean isFollowing(String authorId, String uid);

	List<SimpInfoUserResponse> followByFollowerId(String followerId);

	List<SimpInfoUserResponse> followByFollowingId(String followingId);
}
