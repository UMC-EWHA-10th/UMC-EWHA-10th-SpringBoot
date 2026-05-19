package com.example.umc10th_week4.domain.user.converter;

import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
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


    public static UserResDTO.MyPageResponse toMyPageResponse(User user) {
        return UserResDTO.MyPageResponse.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .point(user.getPoint())
                .build();
    }

    public static User toUser(UserReqDTO.SignUpRequest request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .nickname(request.getNickname())
                .gender(request.getGender())
                .birth(request.getBirth())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static UserResDTO.SignUpResponse toSignUpResponse(User user) {
        return UserResDTO.SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}



