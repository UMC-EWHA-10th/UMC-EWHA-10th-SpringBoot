package com.example.chap4.domain;

import com.example.chap4.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // 진행중, 완료 등

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private com.example.chap4.domain.Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private com.example.chap4.domain.Mission mission;
}
