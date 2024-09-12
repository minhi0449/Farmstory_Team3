package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    private int point;
    private String receiver;
    private String receiverHp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payMethod;
    private String etc;
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;


}
