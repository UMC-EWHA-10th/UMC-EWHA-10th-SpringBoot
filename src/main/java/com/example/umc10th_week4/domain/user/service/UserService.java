package com.example.umc10th_week4.domain.user.service;

import com.example.umc10th_week4.domain.user.converter.UserConverter;
import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.entity.User;
import com.example.umc10th_week4.domain.user.repository.UserRepository;
import com.example.umc10th_week4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th_week4.global.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResDTO.UserInfoResponse getUserInfo(UserReqDTO.UserInfoRequest request) {

        // id로 User 조회, 없으면 예외 발생
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        // Converter로 응답 DTO 만들어서 리턴
        return UserConverter.toUserInfoResponse(user);


    }
    // ↓ 이거 추가!
    public UserResDTO.MyPageResponse getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        return UserConverter.toMyPageResponse(user);
    }
}