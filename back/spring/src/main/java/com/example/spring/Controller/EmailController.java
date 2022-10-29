package com.example.spring.Controller;

import com.example.spring.Service.EmailService;
import com.example.spring.DTO.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    //사용자 이메일로 비밀번호 초기화 인증코드 전송
    @PostMapping("/send")
    public void send(@RequestBody Email email){

        emailService.sendMail(email);
    }
}
