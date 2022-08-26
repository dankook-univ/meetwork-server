package com.github.dankook_univ.meetwork.email.application;

import com.github.dankook_univ.meetwork.email.exceptions.EmailSendException;
import com.github.dankook_univ.meetwork.email.infra.http.request.EmailRequest;
import com.github.dankook_univ.meetwork.email.utils.EmailHandler;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final ProfileServiceImpl profileService;
    @Value("${spring.mail.username}")
    private String address;

    @Override
    public String mailSend(String memberId, String eventId, EmailRequest request) {
        Profile profile = profileService.get(memberId, eventId);

        try {
            EmailHandler mailHandler = new EmailHandler(mailSender);

            mailHandler.setTo(profile.getMember().getEmail());
            mailHandler.setFrom(address);
            mailHandler.setSubject(request.getTitle());
            String htmlContent =
                "<div style=\"display: flex; flex-direction: row; min-width: 50%; padding-right: 20px; border: solid 2px #EE9591; border-radius: 10px; align-items: center;\">"
                    +
                    "<img style=\"display: flex; width: 120px; height: 120px\" src=\"https://kr.object.ncloudstorage.com/meetwork/logo.svg\" />"
                    +
                    "<div style=\"display: flex; flex:1; flex-direction: column; justify-content: center;\">"
                    +
                    "<span style=\"display: flex; font-size: 24px; font-weight: 700;\">"
                    + request.getTitle()
                    + "</span>" +
                    "<span style=\"display: flex; font-size: 20px; font-weight: 600; margin-top: 2px\">"
                    + request.getContent() + "</span>" +
                    "</div>" +
                    "</div>";
            mailHandler.setText(htmlContent, true);

            mailHandler.send();
            return profile.getMember().getEmail();

        } catch (EmailSendException | MessagingException e) {
            log.error("[EmailService Error] 이메일 전송 실패 ({})", e.getMessage());
            return null;
        }
    }
}
