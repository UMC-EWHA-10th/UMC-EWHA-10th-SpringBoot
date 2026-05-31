package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.auth.AuthMember;
import com.example.umc10th.domain.member.dto.AuthReqDTO;
import com.example.umc10th.domain.member.dto.AuthResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.status.ErrorStatus;
import com.example.umc10th.global.apiPayload.exception.GeneralException;
import com.example.umc10th.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResDTO.SignUpResultDTO signUp(AuthReqDTO.SignUpRequestDTO request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new GeneralException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .point(0)
                .build();

        Member savedMember = memberRepository.save(member);

        return AuthResDTO.SignUpResultDTO.builder()
                .memberId(savedMember.getId())
                .email(savedMember.getEmail())
                .nickname(savedMember.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResDTO.LoginResultDTO login(AuthReqDTO.LoginRequestDTO request) {

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new GeneralException(ErrorStatus.MEMBER_LOGIN_FAILED);
        }

        AuthMember authMember = new AuthMember(member);
        String accessToken = jwtUtil.createAccessToken(authMember);

        return AuthResDTO.LoginResultDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .accessToken(accessToken)
                .build();
    }
}