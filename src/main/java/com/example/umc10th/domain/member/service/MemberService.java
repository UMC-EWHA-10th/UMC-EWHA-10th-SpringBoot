package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public MemberResDTO.GetInfo getInfo(Member member) {
        //엔티티 -> Dto
        return MemberConverter.toGetInfoResDTO(member);
    }
}
