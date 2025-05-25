package com.blog.blogservice;

import com.blog.blogservice.controller.AdminController;
import com.blog.blogservice.service.IBlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IBlogService blogService;

	@Test
	void testDashboard_ReturnsStatistics() throws Exception {
		Map<String, Long> stats = new HashMap<>();
		stats.put("total_post", 10L);
		stats.put("sentitive_post", 2L);
		when(blogService.postStas()).thenReturn(stats);
		mockMvc.perform(get("/dashboard"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.message").value("Success"))
				.andExpect(jsonPath("$.data.blog.total_post").value(10))
				.andExpect(jsonPath("$.data.blog.sentitive_post").value(2));
	}
}
