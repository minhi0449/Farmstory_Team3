package com.farmstory.entity;

import com.farmstory.dto.CartRequestDTO;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity                 // 엔티티 객체 정의
@Builder
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;
    private int count;
    private String uid;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void addProduct(Product product) {
        this.product = product;
    }

    public CartRequestDTO toDTO(){
        return CartRequestDTO.builder()
                .product_id(product.getProdNo())
                .count(count)
                .build();
    }
//    @ManyToOne
//    @JoinColumn(name = "uid")
//    private User user;
    public void changeProduct (Product product) {
        this.product = product;
    }
    public void changeCount(int count) {
        this.count = count;
    }
    public void increaseCount(int num) {
        this.count += num;
    }

    public void decreaseCount(int num) {
        this.count -= num;
    }
}
