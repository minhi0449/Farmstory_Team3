package com.farmstory.entity;

import com.farmstory.dto.TermsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
@Table(name = "Terms")
public class Terms {
    @Id
    @Column(name = "termName", columnDefinition = "TEXT")
    private String termName;

    @Column(name = "termContent", columnDefinition = "TEXT")
    private String termContent;

    public TermsDTO toDTO() {
        return TermsDTO.builder()
                .termName(termName)
                .termContent(termContent)
                .build();
    }

}
