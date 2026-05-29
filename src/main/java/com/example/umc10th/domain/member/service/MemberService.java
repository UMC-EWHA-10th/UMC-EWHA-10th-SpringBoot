package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Food;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.repository.TermRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final TermRepository termRepository;
    private final PasswordEncoder passwordEncoder;


    // 마이페이지
    public MemberResDTO.GetInfo getInfo(
            AuthMember member
    ) {
        // 컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member.getMember());
    }

    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        if (memberRepository.findByEmail(dto.email()).isPresent()) {
            throw new MemberException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto, encodedPassword);
        memberRepository.save(member);

        dto.foodNameList().stream()
                .map(name -> foodRepository.findByName(name)
                        .orElseGet(() -> foodRepository.save(Food.builder().name(name).build())))
                .map(food -> MemberFood.builder().member(member).food(food).build())
                .forEach(member::addMemberFood);

        dto.termNameList().stream()
                .map(name -> termRepository.findByName(name)
                        .orElseGet(() -> termRepository.save(Term.builder().name(name).build())))
                .map(term -> MemberTerm.builder().member(member).term(term).build())
                .forEach(member::addMemberTerm);

        return MemberConverter.toSignUp(member);
    }
}
