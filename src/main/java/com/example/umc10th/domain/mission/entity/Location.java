package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예: 안암동
    @Column(nullable = false, length = 30)
    private String name;
}