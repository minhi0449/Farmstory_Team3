package com.farmstory.dto;

import com.farmstory.entity.Terms;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TermsDTO {

    private String termName;
    private String termContent;

    public Terms toEntity() {
        return Terms.builder()
                .termName(termName)
                .termContent(termContent)
                .build();
    }
}
