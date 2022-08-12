package com.github.dankook_univ.meetwork.chat.application;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.persistence.participant.ChatParticipantRepositoryImpl;
import com.github.dankook_univ.meetwork.chat.infra.persistence.room.ChatRoomRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepositoryImpl chatRoomRepository;
    private final ChatParticipantRepositoryImpl chatParticipantRepository;

    private final ProfileServiceImpl profileService;

    @Override
    public List<ChatRoom> getChatRoomList(
        String memberId, String eventId
    ) throws NotFoundProfileException {
        profileService.get(memberId, eventId);

        return chatRoomRepository.getAll(eventId);
    }

    @Override
    public ChatRoom getChatRoom(String memberId, String roomId) {
        return null;
    }

    @Override
    @Transactional
    public ChatRoom create(String memberId, ChatRoomCreateRequest request) {
        Profile profile = profileService.get(memberId, request.getEventId());

        ChatRoom room = chatRoomRepository.save(
            ChatRoom.builder()
                .event(profile.getEvent())
                .organizer(profile)
                .name(request.getName())
                .isPrivate(request.getIsPrivate())
                .build()
        );

        room.appendParticipant(
            chatParticipantRepository.create(
                ChatParticipant.builder()
                    .room(room)
                    .member(profile)
                    .build()
            )
        );

        request.getParticipantIds().stream()
            .map(profileService::getById)
            .forEach(
                (participant) ->
                    room.appendParticipant(
                        chatParticipantRepository.create(
                            ChatParticipant.builder()
                                .room(room)
                                .member(participant)
                                .build()
                        )
                    )
            );

        return room;
    }
}
