package com.chun.springpt.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "sb97213@gmail.com";
    private static int number = 0;

    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000;
    }

    public MimeMessage createMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("ChatPT 비밀번호 변경 인증 코드 입니다.");
            String body = "";
            body += "<div style='width: 500px; height: 500px; padding: 20px;'>";
            body += "<img src='https://blog.kakaocdn.net/dn/bnsz0K/btsDs2x4b4I/akmKesL6I8vtAPkWNVOk2k/img.png' style='width: 350px; height: auto;'/>";
            body += "<h1 style='color: #333; font-size: 24px;'>이메일 인증</h1>";
            body += "<p style='color: #666; font-size: 16px;'>아래의 인증번호를 입력해주세요.</p>";
            body += "<h3 style='color: #333; font-size: 20px;'>" + number + "</h3>";
            body += "</div>";
            message.setText(body, "utf-8", "html");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }
    // 회원가입
    public MimeMessage signUpEmail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("ChatPT 이메일 인증 코드 입니다.");
            String body = "";
            body += "<div style='width: 500px; height: 500px; padding: 20px;'>";
            body += "<img src='https://blog.kakaocdn.net/dn/bnsz0K/btsDs2x4b4I/akmKesL6I8vtAPkWNVOk2k/img.png' style='width: 350px; height: auto;'/>";
            body += "<h1 style='color: #333; font-size: 24px;'>이메일 인증</h1>";
            body += "<p style='color: #666; font-size: 16px;'>아래의 인증번호를 입력해주세요.</p>";
            body += "<h3 style='color: #333; font-size: 20px;'>" + number + "</h3>";
            body += "</div>";
            message.setText(body, "utf-8", "html");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = createMail(mail);

        javaMailSender.send(message);

        return number;
    }
    // 회원가입
    public int sendEmailCheck(String mail) {
        MimeMessage message = signUpEmail(mail);

        javaMailSender.send(message);

        return number;
    }

}
