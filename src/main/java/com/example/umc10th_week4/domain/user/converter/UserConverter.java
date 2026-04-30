package com.example.umc10th_week4.domain.user.converter;

import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.entity.User;

public class UserConverter {

    public static UserResDTO.UserInfoResponse toUserInfoResponse(User user) {
        return UserResDTO.UserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .point(user.getPoint())
                .build();
    }
}