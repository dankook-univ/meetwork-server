package com.github.dankook_univ.meetwork.chat.application.message;

import com.github.dankook_univ.meetwork.chat.application.room.ChatRoomServiceImpl;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.persistence.message.ChatMessageRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomServiceImpl chatRoomService;
    private final ProfileServiceImpl profileService;
    private final ChatMessageRepositoryImpl chatMessageRepository;

    @Override
    public List<ChatMessage> getByRoomId(
        String memberId, String roomId
    ) throws NotParticipatedMemberException {
        chatRoomService.shouldParticipating(memberId, roomId);

        return chatMessageRepository.getByRoomId(roomId);
    }

    @Override
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

        return chatMessage;
    }
}
