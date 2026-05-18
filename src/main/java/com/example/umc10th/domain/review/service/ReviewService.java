package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReplyRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReviewResDTO.CreateReviewResultDTO createReview(ReviewReqDTO.CreateReviewDTO request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        Review review = Review.builder()
                .member(member)
                .store(store)
                .score(request.getScore())
                .body(request.getBody())
                .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewResDTO.CreateReviewResultDTO.builder()
                .reviewId(savedReview.getId())
                .score(savedReview.getScore())
                .body(savedReview.getBody())
                .build();
    }

    public ReviewResDTO.CreateReplyResultDTO createReply(ReviewReqDTO.CreateReplyDTO request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        Reply reply = Reply.builder()
                .review(review)
                .body(request.getBody())
                .build();

        Reply savedReply = replyRepository.save(reply);

        return ReviewResDTO.CreateReplyResultDTO.builder()
                .replyId(savedReply.getId())
                .reviewId(savedReply.getReview().getId())
                .body(savedReply.getBody())
                .build();
    }
}