package com.example.umc10th_week4.domain.user.controller;

import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.service.UserService;
import com.example.umc10th_week4.global.apiPayload.ApiResponse;
import com.example.umc10th_week4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc10th_week4.global.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserContoller {

    private final UserService userService;

    @PostMapping("/me")
    public ApiResponse<UserResDTO.UserInfoResponse> getUserInfo(
            @RequestBody UserReqDTO.UserInfoRequest request
    ) {
        UserResDTO.UserInfoResponse result = userService.getUserInfo(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS, result);
    }
    // ↓ 이거 추가!
    @GetMapping("/my-page")
    public ApiResponse<UserResDTO.MyPageResponse> getMyPage(
            @RequestParam Long userId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS,
                userService.getMyPage(userId));
    }

    @GetMapping("/me")
    public ApiResponse<UserResDTO.MyPageResponse> getMyInfo(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS,
                userService.getMyPage(authMember.getUser().getId()));
    }
}