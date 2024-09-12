package com.farmstory.entity;

import com.farmstory.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodNo;

    private String prodName;
    private String type;
    private int price;
    private int discount;
    private int deliveryfee;
    private int stock;
    private String img1;
    private String img2;
    private String img3;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regdate;

    private String etc;

//    @ManyToOne
//    @JoinColumn(name = "user_uid")
//    @ToString.Exclude
//    private User user;

    public ProductDTO toDTO(){
        return ProductDTO.builder()
                .prodNo(prodNo)
                .prodName(prodName)
                .type(type)
                .price(price)
                .discount(discount)
                .deliveryfee(deliveryfee)
                .stock(stock)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .regdate(regdate)
                .etc(etc)//
                // .user(user)
                .build();
    }
}
