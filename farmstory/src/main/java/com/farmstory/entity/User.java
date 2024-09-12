package com.farmstory.entity;

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
public class User {
    @Id
    private String uid;

    private String pass;
    private String nick;
    private String email;
    private String hp;
    private String grade;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String createAt;
    private String deletedAt;
}
