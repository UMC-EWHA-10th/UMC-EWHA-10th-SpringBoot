package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;


}
