package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.umc10th.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteByName(String name);
    Optional<Member> findByEmail(String email);
    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
}
