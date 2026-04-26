package com.example.umc10th.domain.member.dto;


import com.example.umc10th.domain.member.enums.Gender;

import java.util.List;

public class MemberReqDTO {
    // 정보 조회
    public record GetInfo(Long id) {
    }

    // 회원 가입
    public record Join(
            Terms terms,
            String name,
            Gender gender,
            String birth,
            String address,
            List<String> favorFood
    ) {
    }

    // 회원 가입-약관
    public record Terms(
            Boolean age,
            Boolean service,
            Boolean privacy,
            Boolean location,
            Boolean marketing
    ) {
    }

}