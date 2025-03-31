package com.blog.authservice.dto.mapper;

import com.blog.authservice.dto.response.SimpInfoUserResponse;
import com.blog.authservice.dto.response.UserResponse;
import com.blog.authservice.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserMapper {

    public UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .dob(user.getDob())
                        .gender(user.getGender())
                        .avatar(user.getAvatar())
                        .email(user.getEmail())
                        .noPassword(!StringUtils.hasText(user.getPassword()))
                        .build();
    }

    public SimpInfoUserResponse toSimpInfoUserResponse(User user) {
        return SimpInfoUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .dob(user.getDob())
                .gender(user.getGender())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }
}
