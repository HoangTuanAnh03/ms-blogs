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
        var roleUser = UserResponse.RoleUser.builder()
                .id(user.getRole().getId())
                .name(user.getRole().getName())
                .build();

        return UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .dob(user.getDob())
                        .gender(user.getGender())
                        .address(user.getAddress())
                        .email(user.getEmail())
                        .role(roleUser)
                        .noPassword(!StringUtils.hasText(user.getPassword()))
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build();
    }

    public SimpInfoUserResponse toSimpInfoUserResponse(User user) {
        return SimpInfoUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .dob(user.getDob())
                .gender(user.getGender())
                .address(user.getAddress())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .build();
    }
}
