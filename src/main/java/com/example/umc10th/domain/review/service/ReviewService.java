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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewCursorResultDTO getMyReviewsByIdCursor(
            Long memberId,
            Long cursorId,
            Integer size
    ) {
        int pageSize = size == null ? 10 : size;

        PageRequest pageRequest = PageRequest.of(0, pageSize + 1);

        List<Review> reviews = reviewRepository.findMyReviewsByIdCursor(
                memberId,
                cursorId,
                pageRequest
        );

        boolean hasNext = reviews.size() > pageSize;

        List<Review> content = hasNext
                ? reviews.subList(0, pageSize)
                : reviews;

        String nextCursor = null;

        if (hasNext && !content.isEmpty()) {
            Review lastReview = content.get(content.size() - 1);
            nextCursor = String.valueOf(lastReview.getId());
        }

        List<ReviewResDTO.MyReviewDTO> reviewDTOList = content.stream()
                .map(review -> ReviewResDTO.MyReviewDTO.builder()
                        .reviewId(review.getId())
                        .storeName(review.getStore().getName())
                        .score(review.getScore())
                        .body(review.getBody())
                        .build())
                .toList();

        return ReviewResDTO.MyReviewCursorResultDTO.builder()
                .reviewList(reviewDTOList)
                .listSize(reviewDTOList.size())
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewCursorResultDTO getMyReviewsByScoreCursor(
            Long memberId,
            String cursor,
            Integer size
    ) {
        int pageSize = size == null ? 10 : size;

        Float cursorScore = null;
        Long cursorId = null;

        if (cursor != null && !cursor.isBlank()) {
            String[] cursorParts = cursor.split(":");

            if (cursorParts.length != 2) {
                throw new IllegalArgumentException("커서 형식이 올바르지 않습니다. 예: 4.5:10");
            }

            cursorScore = Float.parseFloat(cursorParts[0]);
            cursorId = Long.parseLong(cursorParts[1]);
        }

        PageRequest pageRequest = PageRequest.of(0, pageSize + 1);

        List<Review> reviews = reviewRepository.findMyReviewsByScoreCursor(
                memberId,
                cursorScore,
                cursorId,
                pageRequest
        );

        boolean hasNext = reviews.size() > pageSize;

        List<Review> content = hasNext
                ? reviews.subList(0, pageSize)
                : reviews;

        String nextCursor = null;

        if (hasNext && !content.isEmpty()) {
            Review lastReview = content.get(content.size() - 1);
            nextCursor = lastReview.getScore() + ":" + lastReview.getId();
        }

        List<ReviewResDTO.MyReviewDTO> reviewDTOList = content.stream()
                .map(review -> ReviewResDTO.MyReviewDTO.builder()
                        .reviewId(review.getId())
                        .storeName(review.getStore().getName())
                        .score(review.getScore())
                        .body(review.getBody())
                        .build())
                .toList();

        return ReviewResDTO.MyReviewCursorResultDTO.builder()
                .reviewList(reviewDTOList)
                .listSize(reviewDTOList.size())
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }
}