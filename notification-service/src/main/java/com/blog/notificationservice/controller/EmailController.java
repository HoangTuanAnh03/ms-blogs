//package com.blog.notificationservice.controller;
//
//
//import com.blog.event.NotificationEvent;
//import com.blog.notificationservice.dto.ApiResponse;
//import com.blog.notificationservice.dto.response.EmailResponse;
//import com.blog.notificationservice.service.EmailService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class EmailController {
//    EmailService emailService;
//
//    @GetMapping("/email/send")
//    ApiResponse<EmailResponse> sendEmail() {
//        NotificationEvent message = new NotificationEvent();
//
//        message.setSubject("hjhhhhhhhhhhhhhh");
//        message.setRecipient("linh019374@yopmail.com");
//        Map<String, String> map = new HashMap<>();
//
//        map.put("code", "Aaaaaaaaaaa");
//        map.put("name", "Tuan ANh");
//
//        message.setParam(map);
//        emailService.sendEmailRegister(message);
//        return ApiResponse.<EmailResponse>builder().data(null).build();
//    }
//}
