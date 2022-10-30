package com.github.dankook_univ.meetwork.chat.infra.http;

import com.github.dankook_univ.meetwork.chat.application.message.ChatMessageServiceImpl;
import com.github.dankook_univ.meetwork.chat.application.room.ChatRoomServiceImpl;
import com.github.dankook_univ.meetwork.chat.domain.message.ChatMessage;
import com.github.dankook_univ.meetwork.chat.domain.room.ChatRoom;
import com.github.dankook_univ.meetwork.chat.exceptions.NotFoundChatRoomPermissionException;
import com.github.dankook_univ.meetwork.chat.exceptions.NotParticipatedMemberException;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.http.request.ChatRoomUpdateRequest;
import com.github.dankook_univ.meetwork.chat.infra.http.request.MessageCreateRequest;
import com.github.dankook_univ.meetwork.chat.infra.http.response.ChatMessageResponse;
import com.github.dankook_univ.meetwork.chat.infra.http.response.ChatRoomResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomServiceImpl chatRoomService;
    private final ChatMessageServiceImpl chatMessageService;

    @GetMapping("/{eventId}")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty Long eventId
    ) {
        return ResponseEntity.ok().body(
            chatRoomService.getChatRoomList(Long.getLong(authentication.getName()), eventId)
                .stream()
                .map(ChatRoom::toResponse)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{eventId}/{roomId}")
    public ResponseEntity<ChatRoomResponse> getChatRoom(
        @ApiIgnore Authentication authentication,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId
    ) throws NotParticipatedMemberException {
        return ResponseEntity.ok().body(
            chatRoomService.getChatRoom(Long.getLong(authentication.getName()), roomId).toResponse()
        );
    }

    @GetMapping("/{eventId}/participant")
    public ResponseEntity<List<ChatRoomResponse>> getParticipatedChatRooms(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty Long eventId
    ) {
        return ResponseEntity.ok().body(
            chatRoomService.getParticipatedChatRoomList(Long.getLong(authentication.getName()),
                    eventId).stream()
                .map(ChatRoom::toResponse)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{eventId}/{roomId}/participants")
    public ResponseEntity<List<ProfileResponse>> getParticipants(
        @ApiIgnore Authentication authentication,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId
    ) throws NotParticipatedMemberException {
        return ResponseEntity.ok().body(
            chatRoomService.getParticipants(Long.getLong(authentication.getName()), roomId).stream()
                .map(Profile::toResponse)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{eventId}/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
        @ApiIgnore Authentication authentication,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId
    ) throws NotParticipatedMemberException {
        return ResponseEntity.ok().body(
            chatMessageService.getByRoomId(Long.getLong(authentication.getName()), roomId).stream()
                .map(ChatMessage::toResponse)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/{eventId}/new")
    public ResponseEntity<ChatRoomResponse> createChatRoom(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty Long eventId,
        @RequestBody @Valid ChatRoomCreateRequest request
    ) {
        return ResponseEntity.ok().body(
            chatRoomService.create(Long.getLong(authentication.getName()), eventId, request)
                .toResponse()
        );
    }

    @PostMapping("/{eventId}/{roomId}/join")
    public ResponseEntity<ChatRoomResponse> joinChatRoom(
        @ApiIgnore Authentication authentication,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId
    ) {
        return ResponseEntity.ok().body(
            chatRoomService.join(Long.getLong(authentication.getName()), roomId).toResponse()
        );
    }

    @PostMapping("/{eventId}/{roomId}/message/new")
    public ResponseEntity<ChatMessageResponse> sendMessage(
        @ApiIgnore Authentication authentication,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId,
        @RequestBody @Valid MessageCreateRequest request
    ) throws NotParticipatedMemberException {
        return ResponseEntity.ok().body(
            chatMessageService.send(
                Long.getLong(authentication.getName()),
                roomId,
                request.getMessage()
            ).toResponse()
        );
    }

    @PutMapping("/{eventId}/{roomId}")
    public ResponseEntity<ChatRoomResponse> update(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty Long eventId,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId,
        @RequestBody @Valid ChatRoomUpdateRequest request
    ) throws NotParticipatedMemberException, NotFoundChatRoomPermissionException {
        return ResponseEntity.ok().body(
            chatRoomService.update(
                Long.getLong(authentication.getName()),
                eventId,
                roomId,
                request
            ).toResponse()
        );
    }

    @DeleteMapping("/{eventId}/{roomId}")
    public ResponseEntity<Boolean> delete(
        @ApiIgnore Authentication authentication,
        @PathVariable("eventId") @Valid @NotEmpty Long eventId,
        @PathVariable("roomId") @Valid @NotEmpty Long roomId
    ) throws NotParticipatedMemberException, NotFoundChatRoomPermissionException {
        return ResponseEntity.ok().body(
            chatRoomService.delete(Long.getLong(authentication.getName()), eventId, roomId)
        );
    }
}
