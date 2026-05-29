package com.example.umc10th.global.security.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.security.dto.KakaoDTO;
import com.example.umc10th.global.security.dto.OAuthDTO;
import com.example.umc10th.global.security.entity.OAuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {
        // (필수) 인증 서버의 일회성 토큰을 이용해 정보 조회 & 유저 객체 생성
        OAuth2User oAuthMember = super.loadUser(userRequest);

        // 소셜 로그인 제공자 & UID 추출
        SocialType providerId;
        String socialUid;
        try {
            providerId = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
            socialUid = String.valueOf(oAuthMember.getAttribute("id"));
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // 카카오 계정 정보 추출 (null 방어)
        Map<String, Object> kakaoAccount = oAuthMember.getAttribute("kakao_account");
        if (kakaoAccount == null) {
            throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        // OAuth 공통 정보 DTO로 매핑
        OAuthDTO dto;
        switch (providerId) {
            case KAKAO -> {
                // email이 없는 경우 socialUid@kakao.com 으로 대체
                Object emailObj = kakaoAccount.get("email");
                String email = emailObj != null ? emailObj.toString() : socialUid + "@kakao.com";
                String name = (profile != null && profile.get("nickname") != null)
                        ? profile.get("nickname").toString()
                        : "카카오유저";
                dto = new KakaoDTO(socialUid, email, name);
            }
            default -> throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // DB 저장: 있다면 그 데이터 가져오고 없으면 새로 저장
        Member member = memberRepository.findBySocialTypeAndSocialUid(providerId, socialUid)
                .orElseGet(() -> {
                    Member newMember = MemberConverter.toMember(dto);
                    return memberRepository.save(newMember);
                });
        return new OAuthMember(member, oAuthMember.getAttributes());
    }
}
