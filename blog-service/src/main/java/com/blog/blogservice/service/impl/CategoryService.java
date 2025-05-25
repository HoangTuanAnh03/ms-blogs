package com.blog.blogservice.service.impl;

import com.blog.blogservice.dto.mapper.CategoryMapper;
import com.blog.blogservice.dto.response.CategoryResponse;
import com.blog.blogservice.repository.CategoryRepository;
import com.blog.blogservice.service.ICategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {

	CategoryRepository categoryRepository;
	CategoryMapper categoryMapper;
	@Override
	public List<CategoryResponse> getAll() {
		return categoryRepository.findAll()
									.stream()
									.map(this.categoryMapper::toCategoryResponse)
									.collect(Collectors.toList());
	}

	@Override
	public CategoryResponse findByCid(Long cid) {
		return categoryMapper.toCategoryResponse(categoryRepository.findFirstById(cid));
	}
}
