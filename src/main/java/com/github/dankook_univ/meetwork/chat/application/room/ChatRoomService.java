package com.github.dankook_univ.meetwork.chat.application.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomPermissionException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomUpdateRequest;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.List;

public interface ChatRoomService {

    List<ChatRoom> getChatRoomList(Long memberId, Long eventId);

    List<ChatRoom> getParticipatedChatRoomList(Long memberId, Long eventId);

    ChatRoom getChatRoom(Long memberId, Long roomId) throws NotParticipatedMemberException;

    List<Profile> getParticipants(
        Long memberId, Long roomId
    ) throws NotParticipatedMemberException;

    ChatRoom create(Long memberId, Long eventId, ChatRoomCreateRequest request);

    ChatRoom update(Long memberId, Long eventId, Long roomId, ChatRoomUpdateRequest request)
        throws NotParticipatedMemberException, NotFoundChatRoomPermissionException;

    ChatRoom join(Long memberId, Long roomId);

    Boolean delete(Long memberId, Long eventId, Long roomId)
        throws NotParticipatedMemberException, NotFoundChatRoomPermissionException;

    void shouldParticipating(Long memberId, Long roomId) throws NotParticipatedMemberException;
}
