package com.example.umc10th.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResDto<T> {
    private List<T> dataList;
    private Integer listSize; //이번 페이지 데이터 수
    private Integer totalPage; //전체 페이지 수, 오프셋
    private Long totalElements; //전체 페이지 데이터 수, 오프셋
    private Boolean isFirst;
    private Boolean isLast;
    private Long nextCursor; //커서
    private Integer nextSubCursor; //별점순 정렬용, 커서
}
