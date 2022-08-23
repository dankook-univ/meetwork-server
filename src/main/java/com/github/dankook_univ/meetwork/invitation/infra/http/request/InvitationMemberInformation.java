package com.github.dankook_univ.meetwork.invitation.infra.http.request;

import com.github.dankook_univ.meetwork.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InvitationMemberInformation {

    Member member;

    Boolean isAdmin;
}
