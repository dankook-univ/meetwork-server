package com.github.dankook_univ.meetwork.auth.domain.auth;

import com.github.dankook_univ.meetwork.auth.domain.role.Role;
import com.github.dankook_univ.meetwork.auth.domain.role.RoleType;
import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends Core {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "auth", cascade = CascadeType.ALL)
    private final List<Role> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuthType type;

    private String clientId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Auth(AuthType type, String clientId, Member member) {
        Assert.isInstanceOf(AuthType.class, type, "type should be instance of AuthType");
        Assert.hasText(clientId, "clientId must not be empty");
        Assert.isInstanceOf(Member.class, member, "member should be instance of Member");

        this.type = type;
        this.clientId = clientId;
        this.member = member;
        this.roles.add(
            Role.builder()
                .auth(this)
                .type(RoleType.General)
                .build()
        );
    }
}
