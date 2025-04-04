package com.blog.notificationservice.service.impl;

import com.blog.event.NotificationEvent;
import com.blog.notificationservice.dto.request.DataMailDto;
import com.blog.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;

    @Value("${api.verify-register}")
    @NonFinal
    String urlVerifyRegister;

    @Value("${api.verify-forgot-password}")
    @NonFinal
    String urlVerifyForgotPassword;

    public void sendEmailRegister(NotificationEvent message) {
        try {
            putPropsDataMail(convertDataMail(message, urlVerifyRegister), "verify_register");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailForgotPassword(NotificationEvent message) {
        try {
            putPropsDataMail(convertDataMail(message, urlVerifyForgotPassword), "verify_forgot_password");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private DataMailDto convertDataMail(NotificationEvent message, String urlBase) throws MessagingException {
        String url = urlBase + message.getParam().get("code");

        Map<String, Object> props = new HashMap<>();
        props.put("name", message.getParam().get("name"));
        props.put("url", url);

        return DataMailDto.builder().to(message.getRecipient()).subject(message.getSubject()).props(props).build();
    }

    private void putPropsDataMail(DataMailDto dataMail, String templateName) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMail.getTo());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html, true);

        mailSender.send(message);
    }
}
