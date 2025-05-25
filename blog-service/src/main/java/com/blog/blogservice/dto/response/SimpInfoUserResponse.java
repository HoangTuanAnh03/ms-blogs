package com.blog.blogservice.dto.response;

import com.blog.blogservice.util.constant.GenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpInfoUserResponse {
	String id;
	String email;
	String name;
	GenderEnum gender;
	LocalDate dob;
	String avatar;
}
