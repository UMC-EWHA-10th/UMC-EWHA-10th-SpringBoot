package com.example.umc10th.domain.member.dto;


import com.example.umc10th.domain.member.enums.Gender;

import java.util.List;

public class MemberReqDTO {

    // 회원 가입
    public record Join(
            String email,
            String password,
            Terms terms,
            String name,
            Gender gender,
            String birth,
            String address,
            String detailAddress,
            String phoneNumber,
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