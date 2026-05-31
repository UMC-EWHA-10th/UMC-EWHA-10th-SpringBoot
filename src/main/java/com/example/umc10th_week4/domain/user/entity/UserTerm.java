package com.example.umc10th_week4.domain.user.entity;

import com.example.umc10th_week4.domain.user.enums.TermType;
import com.example.umc10th_week4.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_term")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTerm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", nullable = false)
    private TermType termType;

    @Column(name = "agreed", nullable = false)
    private boolean agreed;
}