package com.example.chap4.controller;

import com.example.chap4.dto.MissionRequestDTO;
import com.example.chap4.dto.ReviewRequestDTO;
import com.example.chap4.dto.ReviewResponseDTO;
import com.example.chap4.service.MissionService;
import com.example.chap4.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.chap4.dto.MissionResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MissionService missionService;
    private final ReviewService reviewService;

    @PostMapping("/missions")
    public MissionResponseDTO.MyMissionPageResponse getMyMissions(
            @Valid @RequestBody MissionRequestDTO.MyMissionRequest request
    ) {
        return missionService.getMyMissions(request);
    }

    @PostMapping("/reviews")
    public ReviewResponseDTO.MyReviewCursorResponse getMyReviews(
            @Valid @RequestBody ReviewRequestDTO.MyReviewRequest request
    ) {
        return reviewService.getMyReviews(request);
    }
}