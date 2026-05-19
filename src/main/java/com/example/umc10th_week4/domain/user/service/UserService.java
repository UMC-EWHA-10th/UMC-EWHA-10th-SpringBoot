package com.example.umc10th_week4.domain.user.service;

import com.example.umc10th_week4.domain.user.converter.UserConverter;
import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.entity.User;
import com.example.umc10th_week4.domain.user.repository.UserRepository;
import com.example.umc10th_week4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th_week4.global.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResDTO.UserInfoResponse getUserInfo(UserReqDTO.UserInfoRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        return UserConverter.toUserInfoResponse(user);
    }

    public UserResDTO.MyPageResponse getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        return UserConverter.toMyPageResponse(user);
    }

    public UserResDTO.SignUpResponse signUp(UserReqDTO.SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserConverter.toUser(request, encodedPassword);
        userRepository.save(user);
        return UserConverter.toSignUpResponse(user);
    }
}