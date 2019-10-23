package com.hyecheon.photouploader.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSenderImpl javaMailSender;

    public EmailServiceImpl(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSecretMail(String address, String secret) {
        final var message = new SimpleMailMessage();
        message.setFrom("rainbow880616@gmail.com");
        message.setTo(address);
        message.setSubject("로그인 암호");
        message.setText("로그인 암호는 [" + secret + "] 입니다.");
        javaMailSender.send(message);
    }
}
