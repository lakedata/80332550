package com.skmservice.domain.member.repository;

import com.skmservice.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

    Member save(Member member);

    void delete(Member member);
}
