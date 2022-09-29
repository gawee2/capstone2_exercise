package com.example.spring.Controller;

import com.example.spring.Service.EmailService;
import com.example.spring.VO.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public void send(@RequestBody Email email){
        System.out.println("날아온 인증코드: " + email.getMessage());
        System.out.println("날아온 유저아이디: " + email.getUserId());

        emailService.sendMail(email);
    }
}
