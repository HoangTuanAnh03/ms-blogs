package com.blog.authservice.controller;

import com.blog.authservice.advice.exception.PermissionException;
import com.blog.authservice.dto.ApiResponse;
import com.blog.authservice.dto.request.ForgotPasswordRequest;
import com.blog.authservice.dto.request.PasswordCreationRequest;
import com.blog.authservice.dto.request.UpdateUserRequest;
import com.blog.authservice.dto.response.AuthenticationResponse;
import com.blog.authservice.dto.response.ResultPaginationDTO;
import com.blog.authservice.dto.response.SimpInfoUserResponse;
import com.blog.authservice.dto.response.UserResponse;
import com.blog.authservice.entity.User;
import com.blog.authservice.service.UserService;
import com.blog.authservice.util.constant.UsersConstants;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create-password")
    public ApiResponse<?> createPassword(@RequestBody @Valid PasswordCreationRequest request) {
        userService.createPassword(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Password has been created, you could use it to log-in")
                .build();
    }

    @GetMapping("/fetchUserById/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Fetch user by id")
                .data(this.userService.fetchResUserDtoById(id))
                .build();
    }

    @GetMapping("/fetchUserByIdIn")
    public ApiResponse<List<SimpInfoUserResponse>> fetchUserByIdIn(@RequestParam("ids") List<String> ids) {
        return ApiResponse.<List<SimpInfoUserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Fetch users by ids")
                .data(this.userService.fetchUserByIdIn(ids))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Fetch my info")
                .data(this.userService.fetchMyInfo())
                .build();
    }

    @GetMapping("")
    public ApiResponse<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ApiResponse.<ResultPaginationDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Fetch all users")
                .data(this.userService.fetchAllUser(spec, pageable))
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update a user")
                .data(this.userService.handleUpdateUser(id, updateUserRequest))
                .build();
    }

    @PutMapping("/updateHR/{companyId}")
    public ApiResponse<UserResponse> updateHR(@PathVariable(name = "companyId") long companyId) throws PermissionException {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update role HR")
                .data(this.userService.handleUpdateHR(companyId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable("id") String id) {
        boolean isDeleted = userService.handleDeleteUser(id);
        if (isDeleted) {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.CREATED.value())
                    .message(UsersConstants.MESSAGE_200)
                    .data(null)
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .message(UsersConstants.MESSAGE_417_DELETE)
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/forgotPassword")
    ApiResponse<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.forgotPassword(forgotPasswordRequest.getEmail());

        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Verify email register success")
                .data(null)
                .build();
    }
}
