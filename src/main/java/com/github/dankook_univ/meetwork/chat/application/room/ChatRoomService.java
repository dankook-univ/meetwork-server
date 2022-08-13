package com.github.dankook_univ.meetwork.chat.application.room;

import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import java.util.List;

public interface ChatRoomService {

    List<ChatRoom> getChatRoomList(String memberId, String eventId);

    List<ChatRoom> getParticipatedChatRoomList(String memberId, String eventId);

    ChatRoom getChatRoom(String memberId, String roomId) throws NotParticipatedMemberException;

    ChatRoom create(String memberId, ChatRoomCreateRequest request);

    void shouldParticipating(String memberId, String roomId) throws NotParticipatedMemberException;
}
