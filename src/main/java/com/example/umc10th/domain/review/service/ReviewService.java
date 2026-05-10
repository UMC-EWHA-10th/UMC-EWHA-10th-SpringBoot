package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.PageResDto;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewResDto.CreateReview createReview(Long storeId, Long memberId,
                                                  ReviewReqDto.CreateReview request) {

        //데이터 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));


        //Dto -> 엔티티
        Review newReview = ReviewConverter.toReview(request, member, store);

        //리뷰 저장
        Review savedReview = reviewRepository.save(newReview);

        //엔티티 -> Dto
        return ReviewConverter.toCreateReviewDto(savedReview);


    }

    // 내가 작성한 리뷰들 조회
    public PageResDto<ReviewResDto.Review> getReview(Member member, ReviewReqDto.GetReview request) {

        //페이지번호/사이즈 결정
        Pageable pageable = PageRequest.of(0, request.size() != null ? request.size() : 10);

        //내 리뷰 조회
        Page<Review> reviewPage;

        //별점순
        if (request.sortType() == ReviewSortType.STAR_RATING) {
            reviewPage = reviewRepository.findAllByMemberAndStarRatingCursor(member, request.lastStarRating(), request.lastId(), pageable);
        }
        //ID순
        else {
            reviewPage = reviewRepository.findAllByMemberAndIdCursor(member, request.lastId(), pageable);
        }

        //엔티티 -> Dto
        return ReviewConverter.toPageReviewDto(reviewPage);

    }
}
