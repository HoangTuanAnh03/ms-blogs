package com.blog.blogservice.service;

import com.blog.blogservice.dto.mapper.PostMapper;
import com.blog.blogservice.dto.response.PostSummaryResponse;
import com.blog.blogservice.entity.Post;
import com.blog.blogservice.repository.CategoryRepository;
import com.blog.blogservice.repository.FollowRepository;
import com.blog.blogservice.repository.NotificationRepository;
import com.blog.blogservice.repository.PostRepository;
import com.blog.blogservice.service.impl.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class BlogServiceTest {

	@InjectMocks
	private BlogService blogService;

	@Mock
	private PostRepository postRepository;
	@Mock
	private PostMapper postMapper;
	@Mock
	private IPythonService pythonService;
	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private IBlogRedisService blogRedisService;
	@Mock
	private FollowRepository followRepository;
	@Mock
	private NotificationRepository notificationRepository;

	@Test
	void postStas_ShouldReturnCorrectCounts() {
//		Post p1 = new Post(...);
//		p1.setHasSensitiveContent(false);
//		postRepository.save(p1);
//
//		Post p2 = new Post(...);
//		p2.setHasSensitiveContent(true);
//		postRepository.save(p2);
//
//		Map<String, Long> stats = blogService.postStas();
//
//		assertEquals(2L, stats.get("total_post"));
//		assertEquals(1L, stats.get("sentitive_post"));
	}

	@Test
	void testGetAllPostSummary_WithSearchAndCategories() {
		// Arrange
		int page = 0, size = 10;
		String search = "java";
		List<Long> categoryIds = List.of(1L, 2L);

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
		Post mockPost = Post.builder().id("post-id").title("Java Title").content("Java Content").uid("user1").build();
		Page<Post> postPage = new PageImpl<>(List.of(mockPost), pageable, 1);

		Mockito.when(postRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable)))
				.thenReturn(postPage);

		PostSummaryResponse mockResponse = PostSummaryResponse.builder().id("post-id").title("Java Title").build();
		Mockito.when(postMapper.toPostSummaryResponse(Mockito.eq(mockPost), Mockito.any()))
				.thenReturn(mockResponse);

		// Act
		Page<PostSummaryResponse> result = blogService.getAllPostSummary(page, size, search, categoryIds);

		// Assert
		Assertions.assertEquals(1, result.getTotalElements());
		Assertions.assertEquals("post-id", result.getContent().get(0).getId());
	}
}