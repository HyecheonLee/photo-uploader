package com.hyecheon.photouploader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendSecretMail(String address, String secret) {
        final var message = new SimpleMailMessage();
        message.setFrom("rainbow880616@gmail.com");
        message.setTo(address);
        message.setSubject("로그인 암호");
        message.setText("로그인 암호는 [" + secret + "] 입니다.");
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
