package com.example.umc10th_week4.domain.user.service;

import com.example.umc10th_week4.domain.user.converter.UserConverter;
import com.example.umc10th_week4.domain.user.dto.UserReqDTO;
import com.example.umc10th_week4.domain.user.dto.UserResDTO;
import com.example.umc10th_week4.domain.user.entity.FoodPreference;
import com.example.umc10th_week4.domain.user.entity.User;
import com.example.umc10th_week4.domain.user.entity.UserTerm;
import com.example.umc10th_week4.domain.user.enums.TermType;
import com.example.umc10th_week4.domain.user.repository.FoodPreferenceRepository;
import com.example.umc10th_week4.domain.user.repository.UserRepository;
import com.example.umc10th_week4.domain.user.repository.UserTermRepository;
import com.example.umc10th_week4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th_week4.global.exception.ProjectException;
import com.example.umc10th_week4.global.security.AuthMember;
import com.example.umc10th_week4.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FoodPreferenceRepository foodPreferenceRepository; // ← 추가
    private final UserTermRepository userTermRepository;
    private final JwtUtil jwtUtil;
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

        // 이메일 중복 확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ProjectException(GeneralErrorCode.DUPLICATE_EMAIL);
        }

        // 유저 저장
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserConverter.toUser(request, encodedPassword);
        userRepository.save(user);

        // 선호 음식 저장
        if (request.getFoodList() != null && !request.getFoodList().isEmpty()) {
            List<FoodPreference> foodPreferences = request.getFoodList().stream()
                    .map(foodType -> FoodPreference.builder()
                            .user(user)
                            .foodType(foodType)
                            .build())
                    .toList();
            foodPreferenceRepository.saveAll(foodPreferences);
        }

        // 약관 동의 저장
        UserReqDTO.AgreeRequest agree = request.getAgree();
        List<UserTerm> userTerms = List.of(
                UserTerm.builder().user(user).termType(TermType.AGE).agreed(agree.isAge()).build(),
                UserTerm.builder().user(user).termType(TermType.SERVICE).agreed(agree.isService()).build(),
                UserTerm.builder().user(user).termType(TermType.PRIVACY).agreed(agree.isPrivacy()).build(),
                UserTerm.builder().user(user).termType(TermType.LOCATION).agreed(agree.isLocation()).build(),
                UserTerm.builder().user(user).termType(TermType.MARKETING).agreed(agree.isMarketing()).build()
        );
        userTermRepository.saveAll(userTerms);

        return UserConverter.toSignUpResponse(user);
    }

    public UserResDTO.LoginResponse login(UserReqDTO.LoginRequest request) {

        // 이메일로 유저 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }

        // JWT 토큰 발급
        String accessToken = jwtUtil.createAccessToken(new AuthMember(user));

        return UserConverter.toLoginResponse(accessToken);
    }
}