package com.aptproject.springlibraryproject.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {
    private MailUtils() {

    }

    public static SimpleMailMessage createMailMessage(final String mail,
                                                      final String mailFrom,
                                                      final String subject,
                                                      final String text)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        return mailMessage;
    }}
