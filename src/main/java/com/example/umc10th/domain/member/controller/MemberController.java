package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 (Public)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/sign-up")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody @Valid MemberReqDTO.SignUp dto
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.SIGN_UP, memberService.signUp(dto));
    }

    // 마이페이지 (Private)
    @GetMapping("/api/v2/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal AuthMember member
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.OK, memberService.getInfo(member));
    }
}
