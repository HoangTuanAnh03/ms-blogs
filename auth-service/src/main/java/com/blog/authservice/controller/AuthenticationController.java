package com.blog.authservice.controller;

import com.nimbusds.jose.JOSEException;
import com.blog.authservice.advice.exception.IdInvalidException;
import com.blog.authservice.dto.ApiResponse;
import com.blog.authservice.dto.request.AuthenticationRequest;
import com.blog.authservice.dto.request.CreateUserRequest;
import com.blog.authservice.dto.request.IntrospectRequest;
import com.blog.authservice.dto.request.VerifyNewPasswordRequest;
import com.blog.authservice.dto.response.AuthenticationResponse;
import com.blog.authservice.dto.response.IntrospectResponse;
import com.blog.authservice.dto.response.UserResponse;
import com.blog.authservice.service.AuthenticationService;
import com.blog.authservice.service.UserService;
import com.blog.authservice.service.VerifyCodeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserService userService;
    VerifyCodeService verifyCodeService;
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/outbound/authentication")
    ResponseEntity<ApiResponse<AuthenticationResponse>> outboundAuthenticate(
            @RequestParam("code") String code
    ){
        AuthenticationResponse infoAuthenticationDTO = authenticationService.outboundAuthenticate(code);

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(infoAuthenticationDTO)
                .build();

        return ResponseEntity.ok()
                .body(apiResponse);
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);

        logger.debug("fetchCustomerDetails method start");
        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User login")
                .data(authenticationResponse)
                .build();
        logger.debug("fetchCustomerDetails method end");

        return ResponseEntity.ok()
                .body(apiResponse);
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> createNewUser(@Valid @RequestBody CreateUserRequest postManUser) {
        UserResponse userResponse = this.userService.handleCreateUser(postManUser);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Register a new user")
                .data(userResponse)
                .build();
    }

    @PostMapping("/refreshToken")
    ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@CookieValue(name = "refresh_token", defaultValue = "defaultToken") String refresh_token) throws IdInvalidException, ParseException, JOSEException {
        if (refresh_token.equals("defaultToken")) {
            throw new IdInvalidException("You do not have a refresh token in the cookie");
        }

        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(refresh_token);

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Refresh Token")
                .data(authenticationResponse)
                .build();

        return ResponseEntity.ok()
                .body(apiResponse);
    }

    @PostMapping("/logout")
    ResponseEntity<ApiResponse<Void>> logout(@CookieValue(name = "refresh_token", defaultValue = "defaultToken") String refresh_token) throws IdInvalidException, ParseException, JOSEException {
        if (refresh_token.equals("defaultToken")) {
            throw new IdInvalidException("You do not have a refresh token in the cookie");
        }
        authenticationService.logout(refresh_token);

        // Remove cookie
        ResponseCookie deleteCookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("User logout")
                .data(null)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(apiResponse);
    }


    @GetMapping("/verifyRegister")
    ApiResponse<AuthenticationResponse> verifyRegister(@RequestParam(name = "code") String code) {
        AuthenticationResponse authenticationResponse =  authenticationService.createAuthenticationResponse(verifyCodeService.verifyRegister(code));

        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Verify email register success")
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/verifyForgotPassword")
    ApiResponse<AuthenticationResponse> verifyForgotPassword(@RequestBody VerifyNewPasswordRequest request) {
        AuthenticationResponse authenticationResponse =  authenticationService.createAuthenticationResponse(verifyCodeService.verifyForgotPassword(request));

        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Verify email forgot password success")
                .data(authenticationResponse)
                .build();
    }
}