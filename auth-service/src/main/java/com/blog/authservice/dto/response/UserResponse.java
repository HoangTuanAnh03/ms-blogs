package com.blog.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.blog.authservice.util.constant.GenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;
    String name;
    GenderEnum gender;
    String address;
    LocalDate dob;
    @JsonProperty("mobile_number")
    String mobileNumber;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("no_password")
    Boolean noPassword;

    CompanyUser company;
    RoleUser role;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CompanyUser {
        long id;
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RoleUser {
        long id;
        String name;
    }
}