package com.example.umc10th.domain.mission.entity.mapping;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member_mission")
public class MemberMission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
