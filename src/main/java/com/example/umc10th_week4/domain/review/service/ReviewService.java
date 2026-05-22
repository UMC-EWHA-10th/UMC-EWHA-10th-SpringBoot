package com.example.umc10th_week4.domain.review.service;

import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.domain.mission.repository.UserMissionRepository;
import com.example.umc10th_week4.domain.review.converter.ReviewConverter;
import com.example.umc10th_week4.domain.review.dto.ReviewReqDTO;
import com.example.umc10th_week4.domain.review.dto.ReviewResDTO;
import com.example.umc10th_week4.domain.review.entity.Review;
import com.example.umc10th_week4.domain.review.repository.ReviewRepository;
import com.example.umc10th_week4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th_week4.global.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserMissionRepository userMissionRepository;

    @Transactional
    public ReviewResDTO.CreateReviewResponse createReview(ReviewReqDTO.CreateReviewRequest request) {

        // 1. 해당 UserMission 조회 (없으면 예외)
        UserMission userMission = userMissionRepository.findById(request.getUserMissionId())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        // 2. Review 엔티티 생성
        Review review = ReviewConverter.toReview(request, userMission);

        // 3. 저장
        Review savedReview = reviewRepository.save(review);

        // 4. 응답 DTO 반환
        return ReviewConverter.toCreateReviewResponse(savedReview);
    }
}