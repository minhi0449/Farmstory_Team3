package com.farmstory.dto;

import com.farmstory.entity.Product;
import com.farmstory.entity.User;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

import static java.lang.String.format;

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
    private int point;
    private String img1; // 썸네일 (목록) 이미지
    private String img2; // 기본 정보 이미지
    private String img3; // 상세 정보 이미지
    // TODO : 상세 정보 이미지는 List를 이용하여 여러장 받기

    private String regdate;

    private String etc;

    private String uid;
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
                .point(point)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .regdate(regdate)
                .etc(etc)
                .build();
    }
}
