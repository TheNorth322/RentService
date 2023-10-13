package com.example.rentservice.service;

import com.example.rentservice.dto.email.EmailDetails;

public interface IEmailService {
    String sendMail(EmailDetails details);
}
