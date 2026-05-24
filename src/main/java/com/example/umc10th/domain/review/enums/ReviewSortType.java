package com.example.umc10th.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewSortType {
    ID("ID순"),
    STAR_RATING("별점순");
    private final String description;
}
