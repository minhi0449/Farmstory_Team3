package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;
    private String oName;
    private String sName;

    @ManyToOne
    @JoinColumn(name = "artNo")
    private Article article;

    @CreationTimestamp
    private LocalDateTime createAt;
}
