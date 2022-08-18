package com.github.dankook_univ.meetwork.chat.application.message;

import com.github.dankook_univ.meetwork.chat.application.room.ChatRoomServiceImpl;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.persistence.message.ChatMessageRepositoryImpl;
import com.github.dankook_univ.meetwork.chat.infra.socket.response.MessageResponse;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomServiceImpl chatRoomService;
    private final ProfileServiceImpl profileService;
    private final ChatMessageRepositoryImpl chatMessageRepository;
    private final SimpMessagingTemplate template;

    @Override
    public List<ChatMessage> getByRoomId(
        String memberId, String roomId
    ) throws NotParticipatedMemberException {
        chatRoomService.shouldParticipating(memberId, roomId);

        return chatMessageRepository.getByRoomId(roomId);
    }

    @Override
    @Transactional
    public ChatMessage send(
        String memberId, String roomId, String message
    ) throws NotParticipatedMemberException {
        ChatRoom room = chatRoomService.getChatRoom(memberId, roomId);
        Profile profile = profileService.get(memberId, room.getEvent().getId().toString());

        ChatMessage chatMessage = chatMessageRepository.save(
            ChatMessage.builder()
                .room(room)
                .sender(profile)
                .message(message)
                .build()
        );
        room.appendMessage(chatMessage);

        room.getParticipants().stream()
            .map(ChatParticipant::getMember)
            .map(Profile::getMember)
            .map(Member::getId)
            .forEach(targetId -> template.convertAndSend(
                "/subscribe/chat/" + targetId,
                MessageResponse.builder()
                    .room(room)
                    .message(chatMessage)
                    .build()
            ));

        return chatMessage;
    }
}
