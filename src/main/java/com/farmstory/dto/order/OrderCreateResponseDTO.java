package com.farmstory.dto.order;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderCreateResponseDTO {
    private int orderId;
}
