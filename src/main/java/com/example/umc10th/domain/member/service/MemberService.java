package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResDTO.MyPageResultDTO getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return MemberResDTO.MyPageResultDTO.builder()
                .name(member.getNickname())
                .profileUrl(null)
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .build();
    }
}