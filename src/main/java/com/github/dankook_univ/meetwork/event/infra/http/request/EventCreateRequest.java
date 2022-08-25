package com.github.dankook_univ.meetwork.event.infra.http.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EventCreateRequest {

    @NotBlank
    @Length(min = 2)
    String name;

    @NotBlank
    String code;

    String meetingCode;

    @NotBlank
    String organizerNickname;

    String organizerBio;

    MultipartFile organizerProfileImage;

}
