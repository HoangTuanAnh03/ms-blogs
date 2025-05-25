package com.blog.blogservice.service;

import com.blog.blogservice.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
	public List<CategoryResponse> getAll();
	public CategoryResponse findByCid(Long cid);
}
