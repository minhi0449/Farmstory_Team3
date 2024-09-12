package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemNo;

    private int price;
    private int point;
    private int discount;
    private int deliveryFee;
    private int count;
    @ManyToOne
    @JoinColumn(name = "orderNo")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "prodNo")
    private Product product;
}
