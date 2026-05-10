package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDto;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    public MissionResDto.MissionList getMissionList(Member member, MissionReqDto.GetMissionList request) {
        //페이지 번호/사이즈 결정
        Pageable pageable=PageRequest.of(0, request.size()!=null?request.size():10);

        //특정 멤버의 특정 상태 미션 목록 조회
        Page<MemberMission> missionPage=memberMissionRepository.findAllByMemberAndStatus(
                member,
                request.status().toString(),
                request.lastId(),
                pageable
        );

        //엔티티 -> Dto
        return MissionConverter.toMemberMissionListDto(missionPage);
    }

    public MissionResDto.MissionList missionSummary(Member member, MissionReqDto.MissionSummary request) {
        //페이지 번호/사이즈 결정
        Pageable pageable=PageRequest.of(0, request.size()!=null?request.size():10);

        //도전 가능한 미션 목록 조회
        Page<Mission> missionPage=missionRepository.findHomeMissionList(
                member,
                request.locationId(),
                request.lastId(),
                pageable
        );

        //완료한 미션 개수 집계
        Integer completedCount= memberMissionRepository.countCompletedMissionByLocationId(member,  request.locationId());

        //엔티티 -> Dto
        return MissionConverter.toMissionListDto(missionPage, completedCount);
    }

    public MissionResDto.MissionList getOngoingMissionList(MissionReqDto.GetOngoingMissionList request) {

        // 사용자 조회
        Member member=memberRepository.findById(request.memberId())
                .orElseThrow(()->new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        //페이지 번호/사이즈 결정
        Pageable pageable=PageRequest.of(request.page()!=null? request.page()-1:0, request.size()!=null? request.size() : 10);

        // 진행 중인 미션 조회
        Page<MemberMission> ongoingMissions=memberMissionRepository.findAllByMemberAndOngoing(
                member,
                pageable
        );

        // 엔티티 -> Dto
        return MissionConverter.toMemberMissionListDto(ongoingMissions);
    }
}
