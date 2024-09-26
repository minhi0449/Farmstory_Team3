package com.farmstory.dto;

import com.farmstory.entity.Cart;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private int cartNO;
    private int count;
    private int product_id;
    private String uid;

    public Cart toEntity(){
        return Cart.builder()
                .cartNo(cartNO)
                .count(count)
                .build();
    }
}