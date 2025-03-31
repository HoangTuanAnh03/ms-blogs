package com.blog.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.blog.authservice.util.constant.GenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpInfoUserResponse {
    String id;
    String email;
    String name;
    GenderEnum gender;
    String address;
    LocalDate dob;
    @JsonProperty("mobile_number")
    String mobileNumber;
}