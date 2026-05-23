package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    private static final int PAGE_SIZE = 10;
    // 별점 컬럼 최대 5.0보다 큰 값으로 첫 페이지 cursor 시드
    private static final BigDecimal STAR_CURSOR_SEED = new BigDecimal("5.01");

    @Transactional
    public ReviewResDTO.WriteReview writeReview(
            Long storeId,
            ReviewReqDTO.WriteReview dto
    ) {
        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.STORE_NOT_FOUND));

        Reply reply = ReviewConverter.toReply();
        Review review = ReviewConverter.toReview(dto, member, store, reply);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toWriteReview(saved);
    }

    /**
     * 내가 작성한 리뷰 목록 조회 (커서 페이징, 사진 제외)
     * sort=ID  : 리뷰 ID 내림차순. cursorId 만 사용 (cursorStar 무시)
     * sort=STAR: 별점 내림차순, 동점 시 ID 내림차순. cursorStar + cursorId 사용
     */
    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewList getMyReviewList(
            Long memberId,
            ReviewSortType sortType,
            Float cursorStar,
            Long cursorId
    ) {
        long idCursor = cursorId != null ? cursorId : Long.MAX_VALUE;
        Pageable pageable = PageRequest.of(0, PAGE_SIZE + 1);

        List<Review> rows;
        if (sortType == ReviewSortType.STAR) {
            BigDecimal starCursor = cursorStar != null
                    ? BigDecimal.valueOf(cursorStar)
                    : STAR_CURSOR_SEED;
            rows = reviewRepository.findMyReviewsByStarCursor(
                    memberId, starCursor, idCursor, pageable
            );
        } else {
            rows = reviewRepository.findMyReviewsByIdCursor(
                    memberId, idCursor, pageable
            );
        }
        return ReviewConverter.toMyReviewList(rows, PAGE_SIZE, sortType);
    }
}
