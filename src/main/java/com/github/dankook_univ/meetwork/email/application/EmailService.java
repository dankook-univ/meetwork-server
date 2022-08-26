package com.github.dankook_univ.meetwork.email.application;

import com.github.dankook_univ.meetwork.email.infra.http.request.EmailRequest;

public interface EmailService {

    String mailSend(String memberId, String eventId, EmailRequest request);
}
