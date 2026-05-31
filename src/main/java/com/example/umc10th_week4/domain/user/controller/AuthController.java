package com.example.umc10th_week4.domain.user.controller;

import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.service.UserService;
import com.example.umc10th_week4.global.apiPayload.ApiResponse;
import com.example.umc10th_week4.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<UserResDTO.SignUpResponse> signUp(
            @RequestBody @Valid UserReqDTO.SignUpRequest request
    ) {
        UserResDTO.SignUpResponse result = userService.signUp(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS, result);
    }
    @PostMapping("/login")
    public ApiResponse<UserResDTO.LoginResponse> login(
            @RequestBody @Valid UserReqDTO.LoginRequest request
    ) {
        UserResDTO.LoginResponse result = userService.login(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS, result);
    }
}