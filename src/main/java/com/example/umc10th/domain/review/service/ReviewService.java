package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewImage;
import com.example.umc10th.domain.review.repository.ReviewImageRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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
    ReviewReqDto.CreateReview request){

        //데이터 조회
        Member member=memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Store store=storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("해당 가게가 존재하지 않습니다."));


        //Dto -> 엔티티
        Review newReview= ReviewConverter.toReview(request, member, store);

        //리뷰 저장
        Review savedReview=reviewRepository.save(newReview);

        //엔티티 -> Dto
        return ReviewConverter.toReviewResDto(savedReview);


    }
}
