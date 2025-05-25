package com.blog.blogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableFeignClients
public class BlogServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogServiceApplication.class, args);
	}

}
