package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    private final StoreRepository storeRepository;

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
     * 가게 내 미션 생성
     */
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ){
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    /**
     * 가게 내 미션들 조회
     */
    @Transactional(readOnly = true)
    public MissionResDTO.Pagnation<MissionResDTO.GetMission> getMissions(
            Long storeId,
            Integer pageSize,
            String cursor,
            String query
    ){
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Mission> missionList = null;
        String nextCursor;

        if (!cursor.equals("-1")) {
            String[] cursorSplit = cursor.split(":");
            switch(query.toLowerCase()){
                case "id": {
                    long idCursor = Long.parseLong(cursorSplit[1]);
                    missionList = missionRepository.findByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;
                }
                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
            }
        } else {
            missionList = missionRepository.findByStore_IdOrderByIdDesc(storeId, pageRequest);
        }

        List<Mission> content = missionList.getContent();
        Mission last = content.isEmpty() ? null : content.get(content.size() - 1);
        nextCursor = last != null ? last.getId() + ":" + last.getId() : "-1";

        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );
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
     * 내가 진행중인 미션 목록 (오프셋 페이지네이션)
     * memberId는 Request Body로 전달받음
     */
    @Transactional(readOnly = true)
    public MissionResDTO.OngoingMissionList getOngoingMissionList(MissionReqDTO.OngoingMissionList dto) {
        Pageable pageable = PageRequest.of(dto.page(), dto.size());
        Page<MemberMission> page = memberMissionRepository.findOngoingMissionsByMemberId(
                dto.memberId(), pageable
        );
        return MissionConverter.toOngoingMissionList(page);
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
