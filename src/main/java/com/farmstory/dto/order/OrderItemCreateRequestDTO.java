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

    public  OrderItem toEntity() {
        return OrderItem.builder()
                .price(this.getPrice())
                .point(this.getPoint())
                .discount(this.getDiscount())
                .deliveryfee(this.getDeliveryfee())
                .count(this.getCount())
                .build();
    }
}
