package com.blog.blogservice.service.impl;

import com.blog.blogservice.dto.response.SimpInfoUserResponse;
import com.blog.blogservice.entity.Follow;
import com.blog.blogservice.entity.Post;
import com.blog.blogservice.repository.FollowRepository;
import com.blog.blogservice.service.IFollowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowService implements IFollowService {
	AuthService authService;
	FollowRepository followRepository;
	@Override
	public Long countFollowByFollowerId(String followerId) {
		return followRepository.countFollowByFollowerId(followerId);
	}

	@Override
	public Long countFollowByFollowingId(String followingId) {
		return followRepository.countFollowByFollowingId(followingId);
	}

	@Override
	public List<SimpInfoUserResponse> followByFollowerId(String followerId){
		List<Follow> follows = followRepository.findAllByFollowerId(followerId);

		List<String> uids = follows.stream().map(Follow::getFollowingId).distinct().toList();
		return authService.getUserByIds(new ArrayList<>(uids));
	}

	@Override
	public List<SimpInfoUserResponse> followByFollowingId(String followingId){
		List<Follow> follows = followRepository.findAllByFollowingId(followingId);

		List<String> uids = follows.stream().map(Follow::getFollowerId).distinct().toList();
		return authService.getUserByIds(new ArrayList<>(uids));
	}

	@Override
	public Follow follow(String followerId, String followingId) {
		Follow follow = followRepository.findFirstByFollowerIdAndFollowingId(followerId, followingId);
		if(follow != null){
			followRepository.delete(follow);
			return follow;
		}
		return followRepository.save(Follow.builder().followerId(followerId).followingId(followingId).build());
	}

	@Override
	public boolean isFollowing(String authorId, String uid) {
		return followRepository.findFirstByFollowerIdAndFollowingId(uid, authorId) != null;
	}
}
