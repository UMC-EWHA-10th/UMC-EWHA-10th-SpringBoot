package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.auth.AuthMember;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    // JWT 기반 마이페이지 조회
    @GetMapping("/me")
    public ApiResponse<MemberResDTO.MyPageResultDTO> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        MemberResDTO.MyPageResultDTO result = memberService.getMyPage(authMember);

        return ApiResponse.of(MemberSuccessCode.MEMBER_INFO_SUCCESS, result);
    }

    // 기존 Request Body 기반 마이페이지 조회를 유지하고 싶을 때 사용
    @PostMapping("/me")
    public ApiResponse<MemberResDTO.MyPageResultDTO> getMyPageByRequestBody(
            @Valid @RequestBody MemberReqDTO.MyPageRequestDTO request
    ) {
        MemberResDTO.MyPageResultDTO result =
                memberService.getMyPageByMemberId(request.getMemberId());

        return ApiResponse.of(MemberSuccessCode.MEMBER_INFO_SUCCESS, result);
    }
}