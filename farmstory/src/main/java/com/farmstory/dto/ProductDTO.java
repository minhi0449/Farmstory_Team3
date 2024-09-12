package com.farmstory.dto;

import com.farmstory.entity.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class ProductDTO {

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
    private LocalDateTime regdate;
    private String etc;

   // private String uid;

    // 추가필드
    private List<MultipartFile> images;

    public Product toEntity(){
        return Product.builder()
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
                .etc(etc)
                .build();
    }
}
