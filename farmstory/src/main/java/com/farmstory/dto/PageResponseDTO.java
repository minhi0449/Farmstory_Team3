package com.farmstory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {
    private List<T> content; // 페이지의 데이터 목록
    private int currentPage; // 현재 페이지 번호
    private int totalPages;  // 총 페이지 수
    private long totalElements; // 총 요소 수
    private int pageSize; // 페이지당 요소 수
    private boolean isLast; // 마지막 페이지 여부

    public static <T> PageResponseDTO<T> fromPage(Page<T> page) {
        return PageResponseDTO.<T>builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .isLast(page.isLast())
                .build();
    }
}
