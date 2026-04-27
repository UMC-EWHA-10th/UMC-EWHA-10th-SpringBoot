package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// ReviewRequestDTO.java
public class ReviewRequestDTO {
    @Getter
    @Setter
    public static class PostReviewDTO {
        private Integer rating;
        private String content;
    }
}

// ApiResponseDTO.java (성공 처리용)
@Getter
@AllArgsConstructor
public class ApiResponseDTO {
    private boolean success;
}
