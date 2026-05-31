package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.AuthReqDTO;
import com.example.umc10th.domain.member.dto.AuthResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.status.ErrorStatus;
import com.example.umc10th.global.apiPayload.exception.GeneralException;
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
}