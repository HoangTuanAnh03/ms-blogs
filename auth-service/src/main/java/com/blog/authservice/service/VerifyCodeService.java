package com.blog.authservice.service;

import com.blog.authservice.dto.request.VerifyNewPasswordRequest;
import com.blog.authservice.entity.User;
import com.blog.authservice.entity.VerificationCode;

public interface VerifyCodeService {

     void delete (VerificationCode verificationCode);

     VerificationCode findByEmail(String email);

     VerificationCode save(VerificationCode verificationCode);

     Boolean isTimeOutRequired(VerificationCode verificationCode, long ms);

     User verifyRegister(String code);

     User verifyForgotPassword(VerifyNewPasswordRequest request);
}
