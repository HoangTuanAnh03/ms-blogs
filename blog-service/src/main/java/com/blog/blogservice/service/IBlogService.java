package com.blog.blogservice.service;

import com.blog.blogservice.dto.record.CategoryPostCountDTO;
import com.blog.blogservice.dto.request.PostRequest;
import com.blog.blogservice.dto.response.PostResponse;
import com.blog.blogservice.dto.response.PostSummaryAIResponse;
import com.blog.blogservice.dto.response.PostSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IBlogService {

	Page<PostSummaryResponse> getAllPostSummary(int page, int size, String search, List<Long> categories);

	PostResponse create(PostRequest postRequest, String uid);

	PostResponse view(String pid, String uid);

	PostSummaryAIResponse viewSummary(String pid);

	Map<String, Long> postStas();

	List<Map<String, Object>> getPostCountByMonthInLastSixMonths();

	List<CategoryPostCountDTO> getCategoryPostStas();

	PostResponse deletePost(String pid, boolean isAdmin, String uid);

	Page<PostSummaryResponse> getByUid(int page, int size, String uid);
}
