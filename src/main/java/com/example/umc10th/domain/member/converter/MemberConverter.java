package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.mission.enums.Address;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberConverter {

    //엔티티 -> MemberResDto.GetInfo
    public static MemberResDTO.GetInfo toGetInfoResDTO(Member member) {
        return MemberResDTO.GetInfo.builder()
                .name(member.getName())
                .profileUrl(member.getProfileUrl())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .phoneNumberVerified(member.isPhoneNumberVerified())
                .point(member.getPoint())
                .build();
    }

    // MemberReqDto.Join->엔티티
    public static Member toMember(MemberReqDTO.Join dto, String encodedPassword){
        LocalDate birthDate= LocalDate.parse(dto.birth());

        return Member.builder()
                .email(dto.email())
                .password(encodedPassword)
                .name(dto.name())
                .gender(dto.gender())
                .birth(birthDate)
                .address(Address.valueOf(dto.address()))
                .detailAddress(dto.detailAddress())
                .phoneNumber(dto.phoneNumber())
                .social_uid("normal_login")
                .build();

    }

    // MemberReqDto.Join.favorFood->MemberFood
    public static List<MemberFood> toMemberFoodList(List<Food> foodList, Member member){
        return foodList.stream()
                .map(food -> MemberFood.builder()
                        .food(food)
                        .member(member)
                        .build()
                ).collect(Collectors.toList());
    }

    // MemberReqDto.Join.Terms->MemberTerm
    public static List<MemberTerm> toMemberTermList(Map<Term, Boolean> termAgreementMap, Member member){
        return termAgreementMap.entrySet().stream()
                .map(entry->MemberTerm.builder()
                        .term(entry.getKey())
                        .agree(entry.getValue())
                        .member(member)
                        .build())
                .collect(Collectors.toList());
    }

    // 엔티티->MemberResDto.Join
    public static MemberResDTO.Join toJoinResDto(Member member) {
        return MemberResDTO.Join.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.Login toLogin(String accessToken) {
        return MemberResDTO.Login.builder()
                .accessToken(accessToken).build();
    }
}
