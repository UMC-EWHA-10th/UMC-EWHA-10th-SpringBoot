package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.common.BaseEntity;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 5)
    private String name;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Column(name = "address", nullable = false)
    private Address address;

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;

    @Column(name = "social_uid", length = 255)
    private String socialUid;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "profile_url", columnDefinition = "TEXT")
    private String profileUrl;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFood> memberFoodList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberTerm> memberTermList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> memberMissionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    public void addMemberFood(MemberFood memberFood) {
        memberFoodList.add(memberFood);
    }

    public void addMemberTerm(MemberTerm memberTerm) {
        memberTermList.add(memberTerm);
    }
}
