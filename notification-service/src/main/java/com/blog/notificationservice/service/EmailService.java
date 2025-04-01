package com.blog.notificationservice.service;

import com.blog.event.NotificationEvent;


public interface EmailService {
    void sendEmailRegister(NotificationEvent message);

    void sendEmailForgotPassword(NotificationEvent message);
}
