package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artNo;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    private String title;
    private String content;
    private LocalDateTime regdate;
    private int views;
    private String type;
    private String cate;

}
