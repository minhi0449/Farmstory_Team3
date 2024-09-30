package com.farmstory.dto;

import com.farmstory.entity.Cart;
import lombok.*;

import java.util.List;
import java.util.stream.Collector;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {

    private int cartNO;
    private int count;
    private String uid;
    private ProductDTO product;


    public Cart toEntity(){
        return Cart.builder()
                .cartNo(cartNO)
                .count(count)
                .build();
    }

}