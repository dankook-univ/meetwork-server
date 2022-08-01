package com.github.dankook_univ.meetwork.member.infra.persistence;

import com.github.dankook_univ.meetwork.member.domain.Member;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

}
