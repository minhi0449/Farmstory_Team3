package com.farmstory.dto;

import com.farmstory.entity.User;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String uid;
    private String pass;
    private String nick;
    private String name;
    private String role;
    private String email;
    private String hp;
    private String grade; // 사용자 등급 (권한 | 사용자,관리자)
    private String zip; // 우편번호
    private String addr1; // 주소 1 : 도로명 주소
    private String addr2; // 주소 2: 추가적인 주소 정보
    private String regip; // 등록 IP (사용자가 계정을 등록할 때의 IP 주소를 저장)
    private String createdAt; // 계정 생성 일시
    private String deletedAt; // 계정 삭제 일시

    public User toEntity() {
        return User.builder()
                .uid(uid)
                .pass(pass)
                .name(name)
                .role(role)
                .nick(nick)
                .email(email)
                .hp(hp)
                .grade(grade)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .regip(regip)
                .createAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}







