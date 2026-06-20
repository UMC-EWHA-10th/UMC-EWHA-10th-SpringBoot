package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.FoodName;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.FoodErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.repository.TermRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
import com.example.umc10th.domain.member.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final TermRepository termRepository;
    private final JwtUtil jwtUtil;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberTermRepository memberTermRepository;

    public MemberResDTO.GetInfo getInfo(AuthMember member) {
        //엔티티 -> Dto
        return MemberConverter.toGetInfoResDTO(member.getMember());
    }

    @Transactional
    public MemberResDTO.Join join(MemberReqDTO.Join dto) {

        //비밀번호 암호화
        String encodedPassword=passwordEncoder.encode(dto.password());
        //Dto->엔티티
        Member newMember=MemberConverter.toMember(dto, encodedPassword);

        //음식
        List<Food> foodList=dto.favorFood().stream()
                .map(foodString-> {
                    FoodName foodEnum = FoodName.valueOf(foodString);

                    return foodRepository.findByName(foodEnum)
                            .orElseThrow(() -> new MemberException(FoodErrorCode.FOOD_CATEGORY_NOT_FOUND));
                })
                .toList();
        List<MemberFood> memberFoodList=MemberConverter.toMemberFoodList(foodList, newMember);

        //약관
        List<Term> allTerms=termRepository.findAll();
        Map<Term, Boolean> termAgreementMap=allTerms.stream()
                .collect(Collectors.toMap(term -> term, term -> switch (term.getName()){
                    case AGE_OVER_14 -> dto.terms().age();
                    case SERVICE_TERM     -> dto.terms().service();
                    case PRIVACY_POLICY   -> dto.terms().privacy();
                    case LOCATION_INFO    -> dto.terms().location();
                    case MARKETING_CONSENT -> dto.terms().marketing();
                    default -> false;
        }));
        List<MemberTerm> memberTermList=MemberConverter.toMemberTermList(termAgreementMap, newMember);

        //DB 저장
        Member savedMember=memberRepository.save(newMember);
        memberFoodRepository.saveAll(memberFoodList);
        memberTermRepository.saveAll(memberTermList);

        //엔티티->Dto
        return MemberConverter.toJoinResDto(savedMember);
    }

    @Transactional
    public MemberResDTO.Login login(MemberReqDTO.Login dto) {

        //이메일 확인
        Member member=memberRepository.findByEmail(dto.email())
                .orElseThrow(()->new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        //비밀번호 확인
        if(!passwordEncoder.matches(dto.password(),member.getPassword())){
            throw new MemberException(MemberErrorCode.INCORRECT_PASSWORD);
        }

        //member->authmember
        AuthMember authMember=new AuthMember(member);

        //accessToken 생성
        String accessToken=jwtUtil.createAccessToken(authMember);

        //토큰 반환
        return MemberResDTO.Login.from(accessToken);

    }
}

