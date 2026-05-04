package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    private static final int PAGE_SIZE = 15;

    @Transactional
    public MissionResDTO.CompleteMission completeMission(
            Long missionId,
            MissionReqDTO.CompleteMission dto
    ) {
        MemberMission memberMission = memberMissionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MEMBER_MISSION_NOT_FOUND));
        return MissionConverter.toCompleteMission(memberMission, dto.status());
    }

    /**
     * 내 미션 목록 (진행중/완료) 커서 페이징
     * @param status "ONGOING" | "DONE"
     * @param cursorPoint null 이면 첫 페이지 (Integer.MAX_VALUE 사용)
     * @param cursorId    null 이면 첫 페이지 (Long.MAX_VALUE 사용)
     */
    @Transactional(readOnly = true)
    public MissionResDTO.MyMissionList getMyMissionList(
            Long memberId,
            String status,
            Integer cursorPoint,
            Long cursorId
    ) {
        boolean isComplete = "DONE".equalsIgnoreCase(status) || "COMPLETE".equalsIgnoreCase(status);
        int point = cursorPoint != null ? cursorPoint : Integer.MAX_VALUE;
        long id = cursorId != null ? cursorId : Long.MAX_VALUE;

        // hasNext 판단을 위해 PAGE_SIZE + 1 만큼 가져옴
        Pageable pageable = PageRequest.of(0, PAGE_SIZE + 1);
        List<MemberMission> rows = memberMissionRepository.findMyMissionsByCursor(
                memberId, isComplete, point, id, pageable
        );
        return MissionConverter.toMyMissionList(rows, PAGE_SIZE);
    }

    /**
     * 홈 화면: 선택 지역에서 도전 가능한 미션 목록 (커서 페이징)
     */
    @Transactional(readOnly = true)
    public MissionResDTO.HomeMissionList getHomeMissionList(
            Long memberId,
            Long locationId,
            Integer cursorPoint,
            Long cursorId
    ) {
        int point = cursorPoint != null ? cursorPoint : Integer.MAX_VALUE;
        long id = cursorId != null ? cursorId : Long.MAX_VALUE;

        Pageable pageable = PageRequest.of(0, PAGE_SIZE + 1);
        List<Mission> rows = missionRepository.findHomeMissionsByCursor(
                memberId, locationId, LocalDate.now(), point, id, pageable
        );
        return MissionConverter.toHomeMissionList(rows, PAGE_SIZE);
    }
}
