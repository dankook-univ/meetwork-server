package com.github.dankook_univ.meetwork.auth.domain.role;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.common.domain.Core;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Role extends Core {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Auth auth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType type;

    @Builder
    public Role(Auth auth, RoleType type) {
        this.auth = auth;
        this.type = type;
    }
}
