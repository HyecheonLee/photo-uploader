package com.hyecheon.photouploader.service;

public interface EmailService {
    public void sendSecretMail(String address, String secret);
}
