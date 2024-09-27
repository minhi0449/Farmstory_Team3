package com.farmstory.entity;

import com.farmstory.dto.ProductDTO;
import com.farmstory.exception.OutOfStockException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private int point;
    private String img1;
    private String img2;
    private String img3;

    private String etc;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private String regdate;


    @ManyToOne
    @JoinColumn(name = "user_uid")
    @ToString.Exclude
    private User user;

//    @OneToMany(mappedBy="prodNo")
//    private List<ProdImage> prodImage;

    public void increaseStock(int num) {
        this.stock += num;
    }

    public void decreaseStock(int num) {
        if (this.stock - num < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.");
        }

        this.stock -= num;
    }

    public ProductDTO toDTO() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ProductDTO.builder()
                .prodNo(prodNo)
                .prodName(prodName)
                .type(type)
                .price(price)
                .discount(discount)
                .deliveryfee(deliveryfee)
                .stock(stock)
                .point(point)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .regdate(regdate.substring(0, 19))
                .etc(etc)
                .build();
    }

    public void addUser(User user) {
        this.user = user;
    }
}
