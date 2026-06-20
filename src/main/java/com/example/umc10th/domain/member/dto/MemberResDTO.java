package com.example.umc10th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    // 정보 조회
    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Boolean phoneNumberVerified,
            Integer point
    ){}

    // 회원가입
    @Builder
    public record Join(
            Long id,
            LocalDateTime createdAt
    ){}

    //로그인
    @Builder
    public record Login(
            String accessToken
    ){
        public static Login from(String accessToken){
            return new Login(accessToken);
        }
    }
}
