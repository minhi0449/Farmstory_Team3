package com.farmstory.dto.order;

import com.farmstory.entity.Order;
import com.farmstory.enums.PayMethod;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreateRequestDTO {
    private int point;
    private String receiver;
    private String receiverHp;
    private String zip;
    private String addr1;
    private String addr2;
    private PayMethod payMethod;
    private String etc;
    private String uid;
    @Builder.Default
    private List<OrderItemCreateRequestDTO> orderItems = new ArrayList<>();

    public static OrderCreateRequestDTO fromEntity(Order order) {
        return OrderCreateRequestDTO.builder()
                .point(order.getPoint())
                .receiver(order.getReceiver())
                .receiverHp(order.getReceiverHp())
                .zip(order.getZip())
                .addr1(order.getAddr1())
                .addr2(order.getAddr2())
                .payMethod(order.getPayMethod())
                .etc(order.getEtc())
                .build();
    }

    public Order toEntity() {
        return Order.builder()
                .point(this.getPoint())
                .receiver(this.getReceiver())
                .receiverHp(this.getReceiverHp())
                .zip(this.getZip())
                .addr1(this.getAddr1())
                .addr2(this.getAddr2())
                .payMethod(this.getPayMethod())
                .etc(this.getEtc())
                .build();
    }
}
