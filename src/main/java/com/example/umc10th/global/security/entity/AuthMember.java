package com.example.umc10th.global.security.entity;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        // 일반 로그인(NONE)은 email, 소셜 로그인은 socialUid를 JWT subject로 사용
        if (member.getSocialType() == SocialType.NONE) {
            return member.getEmail();
        }
        return member.getSocialUid();
    }
}
