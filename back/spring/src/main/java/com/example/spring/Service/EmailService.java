package com.example.spring.Service;

import com.example.spring.DTO.Email;

public interface EmailService {
    public void sendMail(Email email);
    public String getUserMail(String userId);
}
