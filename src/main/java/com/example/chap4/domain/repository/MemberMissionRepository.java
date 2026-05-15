package domain.repository;

import domain.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 특정 회원의 진행 중 또는 완료된 미션 목록을 페이징하여 조회
    @Query(value = "SELECT mm FROM MemberMission mm JOIN FETCH mm.mission " +
            "WHERE mm.member.id = :memberId AND mm.status = :status",
            countQuery = "SELECT count(mm) FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.status = :status")
    Page<MemberMission> findByMemberAndStatus(@Param("memberId") Long memberId,
                                              @Param("status") String status,
                                              Pageable pageable);
}