package com.github.dankook_univ.meetwork.chat.application;

import com.github.dankook_univ.meetwork.chat.domain.participant.ChatParticipant;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.AlreadyChatRoomNameException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.persistence.participant.ChatParticipantRepositoryImpl;
import com.github.dankook_univ.meetwork.chat.infra.persistence.room.ChatRoomRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
        Profile profile = profileService.get(memberId, eventId);

        List<UUID> joinedRoomIds = chatParticipantRepository.getByParticipantId(
                profile.getId().toString()
            ).stream()
            .map(ChatParticipant::getRoom)
            .map(ChatRoom::getId)
            .collect(Collectors.toList());

        return chatRoomRepository.getAll(eventId).stream()
            .filter(room -> !room.getIsPrivate() || joinedRoomIds.contains(room.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoom> getParticipatedChatRoomList(
        String memberId, String eventId
    ) throws NotFoundProfileException {
        Profile profile = profileService.get(memberId, eventId);

        return chatParticipantRepository.getByParticipantId(profile.getId().toString()).stream()
            .map(ChatParticipant::getRoom)
            .collect(Collectors.toList());
    }

    @Override
    public ChatRoom getChatRoom(
        String memberId, String roomId
    ) throws NotParticipatedMemberException {
        shouldParticipating(memberId, roomId);

        return chatRoomRepository.getById(roomId).orElseThrow(NotFoundChatRoomException::new);
    }

    @Override
    @Transactional
    public ChatRoom create(String memberId, ChatRoomCreateRequest request) {
        if (chatRoomRepository.getByName(request.getName()).isPresent()) {
            throw new AlreadyChatRoomNameException();
        }

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

        if (request.getParticipantIds() != null) {
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
        }

        return room;
    }

    private void shouldParticipating(
        String memberId, String roomId
    ) throws NotParticipatedMemberException {
        ChatRoom room = chatRoomRepository.getById(roomId)
            .orElseThrow(NotFoundChatRoomException::new);
        Profile participant = profileService.get(memberId, room.getEvent().getId().toString());

        if (chatParticipantRepository.getByParticipantIdAndRoomId(
            participant.getId().toString(),
            roomId).isEmpty()
        ) {
            throw new NotParticipatedMemberException();
        }
    }
}
