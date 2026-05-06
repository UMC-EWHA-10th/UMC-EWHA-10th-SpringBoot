package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/me")
    public ApiResponse<MemberResDTO.MyPageResultDTO> getMyPage(
            @RequestBody MemberReqDTO.MyPageRequestDTO request
    ) {
        MemberResDTO.MyPageResultDTO result = memberService.getMyPage(request.getId());

        return ApiResponse.of(MemberSuccessCode.MEMBER_INFO_SUCCESS, result);
    }
}