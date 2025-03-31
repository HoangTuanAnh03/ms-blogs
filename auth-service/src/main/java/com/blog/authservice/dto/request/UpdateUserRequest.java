package com.blog.authservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.blog.authservice.util.constant.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String name;

    @Past(message = "Date of birth must be before current date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    String avatar;
}