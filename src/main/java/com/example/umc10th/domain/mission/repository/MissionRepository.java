package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    /**
     * 홈 화면: 선택한 지역에서 도전 가능한 미션 (커서 페이징)
     * - 사용자가 아직 도전하지 않은 미션 (member_mission 레코드 없음 = NONE)
     * - 마감일 >= 오늘
     * - 정렬: reward_point DESC, mission_id DESC
     */
    @Query("SELECT mi FROM Mission mi " +
            "JOIN FETCH mi.store s " +
            "JOIN FETCH s.location loc " +
            "WHERE s.location.id = :locationId " +
            "AND mi.deadline >= :today " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM MemberMission mm " +
            "    WHERE mm.member.id = :memberId AND mm.mission = mi" +
            ") " +
            "AND (mi.point < :cursorPoint " +
            "     OR (mi.point = :cursorPoint AND mi.id < :cursorId)) " +
            "ORDER BY mi.point DESC, mi.id DESC")
    List<Mission> findHomeMissionsByCursor(
            @Param("memberId") Long memberId,
            @Param("locationId") Long locationId,
            @Param("today") LocalDate today,
            @Param("cursorPoint") Integer cursorPoint,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
