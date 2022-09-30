package com.example.spring.Service;

import com.example.spring.DTO.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService{

    private final MemberService memberService;

    @Autowired
    public EmailServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void sendMail(Email email) {
        System.out.println("메일 발송 시작");

        //고정
        String senderName = "운동매칭 앱";    //발신자 이름
        String senderMail = "infomjucapstone2@gmail.com";    //발신자 이메일 주소
        String mailPw = "dvtzgqxvfaawokzk";
        String subject = "운동매칭 앱 비번 초기화 인증코드";            //제목

        //가변
        String msg = email.getMessage();
        String receiveMail = getUserMail(email.getUserId());    //수신자 이메일 주소

        //여기 데이터로 이메일 보내면 될듯
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        System.out.println("메일 세션 생성 시작");
        Session mailSession = Session.getInstance(props,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderMail, mailPw);
                    }
                });
        System.out.println("메일 세션 생성 완료");
        try{
            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(senderMail, MimeUtility.encodeText(senderMail, "UTF-8", "B"))); // 한글의 경우 encoding 필요
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiveMail)
            );
            message.setSubject(subject);
            message.setContent(msg, "text/html;charset=UTF-8"); // 내용 설정 (HTML 형식)
            message.setSentDate(new java.util.Date());

            Transport t = mailSession.getTransport("smtp");
            t.connect(senderMail, mailPw);
            t.sendMessage(message, message.getAllRecipients());
            t.close();

            System.out.println("메일 발송 완료");
        }catch (Exception e){
            System.out.println(e.getMessage().toString());

        }
    }

    @Override
    public String getUserMail(String userId) {
        return memberService.findOne(userId).get().getEmail();
    }
}
