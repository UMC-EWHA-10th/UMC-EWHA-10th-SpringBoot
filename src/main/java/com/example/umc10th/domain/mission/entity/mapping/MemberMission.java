package com.example.umc10th.domain.mission.entity.mapping;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 회원의 미션인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 어떤 미션인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    // 진행중 / 완료
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionStatus status;
}