package com.github.dankook_univ.meetwork.email.utils;

import com.github.dankook_univ.meetwork.email.exceptions.EmailSendException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class EmailHandler {

    private final JavaMailSender sender;
    private final MimeMessage message;
    private final MimeMessageHelper messageHelper;

    public EmailHandler(JavaMailSender jSender) throws MessagingException {
        this.sender = jSender;
        message = jSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }

    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    public void send() {
        try {
            sender.send(message);
        } catch (Exception e) {
            throw new EmailSendException();
        }
    }
}
