package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private int deliveryfee;
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderNo")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodNo")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public void registerOrder(Order order) {
        this.order = order;
    }

    public void registerProduct(Product product) {
        this.product = product;
    }
}
