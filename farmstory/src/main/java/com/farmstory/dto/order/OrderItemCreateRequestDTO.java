package com.farmstory.dto.order;

import com.farmstory.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreateRequestDTO {
    private int price;
    private int point;
    private int discount;
    private int deliveryfee;
    private int count;
    private int productId;

    public static OrderItem toEntity(OrderItemCreateRequestDTO dto) {
        return OrderItem.builder()
                .price(dto.getPrice())
                .point(dto.getPoint())
                .discount(dto.getDiscount())
                .deliveryfee(dto.getDeliveryfee())
                .count(dto.getCount())
                .build();
    }
}
