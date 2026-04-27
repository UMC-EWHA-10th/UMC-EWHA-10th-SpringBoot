package controller;

import dto.ApiResponseDTO;
import dto.MemberRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    // 1. 회원가입
    @PostMapping("/signup")
    public ApiResponseDTO join(@RequestBody MemberRequestDTO.JoinDTO request) {
        // 실제 로직은 Service에서 처리하겠지만, 일단 성공 응답 반환
        return new ApiResponseDTO(true);
    }

    // 2. 내 정보 수정
    @PatchMapping("/me")
    public ApiResponseDTO updateProfile(@RequestBody MemberRequestDTO.UpdateProfileDTO request) {
        return new ApiResponseDTO(true);
    }

    // 3. 미션 성공 처리
    @PostMapping("/missions/{missionId}/complete")
    public ApiResponseDTO completeMission(@PathVariable Long missionId) {
        return new ApiResponseDTO(true);
    }

    // 4. 리뷰 작성
    @PostMapping("/reviews")
    public ApiResponseDTO postReview(@RequestBody ReviewRequestDTO.PostReviewDTO request) {
        return new ApiResponseDTO(true);
    }
}