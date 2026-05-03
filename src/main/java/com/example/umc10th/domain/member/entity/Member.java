package com.example.umc10th.domain.member.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private int point;
    private String phoneNumber;
    private String profileUrl;

    public Member(String email, String name, int point, String phoneNumber, String profileUrl) {
        this.email = email;
        this.name = name;
        this.point = point;
        this.phoneNumber = phoneNumber;
        this.profileUrl = profileUrl;
    }
}