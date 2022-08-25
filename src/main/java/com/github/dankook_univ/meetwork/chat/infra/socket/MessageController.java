package com.github.dankook_univ.meetwork.chat.infra.socket;

import com.github.dankook_univ.meetwork.auth.application.token.TokenProviderImpl;
import com.github.dankook_univ.meetwork.chat.application.message.ChatMessageServiceImpl;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.socket.request.MessageRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatMessageServiceImpl chatMessageService;
    private final TokenProviderImpl tokenProvider;

    @MessageMapping("/chat/message")
    @Transactional
    public void message(
        @Valid MessageRequest request,
        @Header("Authorization") String token
    ) throws NotParticipatedMemberException {
        Member member = tokenProvider.parse(token.substring(7)).getMember();

        chatMessageService.send(
            member.getId().toString(),
            request.getRoomId(),
            request.getMessage()
        );
    }
}