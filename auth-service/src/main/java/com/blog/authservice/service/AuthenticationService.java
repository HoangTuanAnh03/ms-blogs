package com.blog.authservice.service;

import com.nimbusds.jose.JOSEException;
import com.blog.authservice.dto.request.AuthenticationRequest;
import com.blog.authservice.dto.request.IntrospectRequest;
import com.blog.authservice.dto.response.AuthenticationResponse;
import com.blog.authservice.dto.response.IntrospectResponse;
import com.blog.authservice.entity.User;

import java.text.ParseException;

public interface AuthenticationService {
    /**
     * @param introspectRequest -IntrospectRequest Object
     * @return boolean indicating if the token is valid or not
     */
    IntrospectResponse introspect(IntrospectRequest introspectRequest);

    /**
     * @param request -AuthenticationRequest Object
     * @return User Details based on a given email and password
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * @param refreshToken - refreshToken get from cookie
     */
    void logout(String refreshToken) throws ParseException, JOSEException;

    /**
     * @param refreshToken - refreshToken get from cookie
     * @return User Details based on a given refreshToken
     */
    AuthenticationResponse refreshToken(String refreshToken) throws ParseException, JOSEException;

    /**
     * @param user - User Object
     * @return Convert User Object to InfoAuthenticationDTO Object
     */
    AuthenticationResponse createAuthenticationResponse(User user);

    AuthenticationResponse outboundAuthenticate(String code);
}
