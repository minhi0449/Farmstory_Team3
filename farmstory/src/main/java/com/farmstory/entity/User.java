package com.farmstory.entity;

import com.farmstory.dto.UserDTO;
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
@Table(name = "User")
public class User {
    @Id
    private String uid;

    private String pass;
    private String nick;
    private String name;
    private String role;
    private String email;
    private String hp;
    private String grade;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String createAt;
    private String deletedAt;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .uid(uid)
                .name(name)
                .role(role)
                .pass(pass)
                .nick(nick)
                .email(email)
                .hp(hp)
                .grade(grade)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .regip(regip)
                .createdAt(createAt)
                .deletedAt(deletedAt)
                .build();
    }
}
