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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
    // 내 리뷰 목록 조회 (커서 기반)
    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewListResponse getMyReviews(Long userId, String cursor, String query, int pageSize) {

        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> slice;
        String nextCursor;

        if (cursor.equals("-1")) {
            // 처음 조회 (커서 없음)
            if (query.equals("star")) {
                slice = reviewRepository.findByUserMission_User_IdOrderByRatingDescIdDesc(userId, pageRequest);
            } else {
                slice = reviewRepository.findByUserMission_User_IdOrderByIdDesc(userId, pageRequest);
            }
        } else {
            // 커서 있을 때
            String[] parts = cursor.split(":");
            if (query.equals("star")) {
                Integer rating = Integer.parseInt(parts[1]);
                Long id = Long.parseLong(parts[2]);
                slice = reviewRepository.findByUserIdOrderByRatingDescWithCursor(userId, rating, id, pageRequest);
            } else {
                Long id = Long.parseLong(parts[1]);
                slice = reviewRepository.findByUserMission_User_IdAndIdLessThanOrderByIdDesc(userId, id, pageRequest);
            }
        }

        // 다음 커서 계산
        if (slice.hasNext()) {
            Review last = slice.getContent().get(slice.getContent().size() - 1);
            if (query.equals("star")) {
                nextCursor = "star:" + last.getRating() + ":" + last.getId();
            } else {
                nextCursor = "id:" + last.getId();
            }
        } else {
            nextCursor = null;
        }


        return ReviewConverter.toMyReviewListResponse(slice, nextCursor);
    }

}