package com.example.umc10th_week4.domain.review.entity;
import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mission_id", nullable = false)
    private UserMission userMission;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "content")
    private String content;
}