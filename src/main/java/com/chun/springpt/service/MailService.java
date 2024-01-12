package com.chun.springpt.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
            message.setSubject("이메일 인증");
            String body = "";
            body += "<div style='width: 500px; height: 500px; border: 1px solid black;'>";
            body += "<h1>이메일 인증</h1>";
            body += "<p>아래의 인증번호를 입력해주세요.</p>";
            body += "<h3>" + number + "</h3>";
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
}
