package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    /**
     * 내가 진행중/완료한 미션 목록 (커서 페이징)
     * 정렬: reward_point DESC, mission_id DESC
     * 커서: (cursorPoint, cursorId) 미만 항목만 반환 (첫 페이지는 MAX 값 전달)
     */
    @Query("SELECT mm FROM MemberMission mm " +
            "JOIN FETCH mm.mission mi " +
            "JOIN FETCH mi.store s " +
            "WHERE mm.member.id = :memberId " +
            "AND mm.isComplete = :isComplete " +
            "AND (mi.point < :cursorPoint " +
            "     OR (mi.point = :cursorPoint AND mi.id < :cursorId)) " +
            "ORDER BY mi.point DESC, mi.id DESC")
    List<MemberMission> findMyMissionsByCursor(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete,
            @Param("cursorPoint") Integer cursorPoint,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * 내가 진행중인 미션 목록 (오프셋 페이지네이션)
     * 정렬: mission.id DESC (최근 도전 순)
     */
    @Query(
            value = "SELECT mm FROM MemberMission mm " +
                    "JOIN FETCH mm.mission mi " +
                    "JOIN FETCH mi.store s " +
                    "WHERE mm.member.id = :memberId " +
                    "AND mm.isComplete = false " +
                    "ORDER BY mi.id DESC",
            countQuery = "SELECT COUNT(mm) FROM MemberMission mm " +
                    "WHERE mm.member.id = :memberId " +
                    "AND mm.isComplete = false"
    )
    Page<MemberMission> findOngoingMissionsByMemberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
