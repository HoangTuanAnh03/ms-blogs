package com.blog.notificationservice.dto;

/**
 * @param uid
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountsMsgDto(String uid, String name, String email, String mobileNumber) {
}
