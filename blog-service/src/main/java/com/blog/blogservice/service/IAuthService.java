package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.SimpInfoUserResponse;

import java.util.List;

public interface IAuthService {
	public List<SimpInfoUserResponse> getUserByIds(List<String> ids);
}
