package com.farmstory.apiController;

import com.farmstory.dto.order.OrderCreateRequestDTO;
import com.farmstory.dto.order.OrderCreateResponseDTO;
import com.farmstory.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderCreateResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO orderDTO) {
        log.debug("createOrder start");
        log.debug("orderDTO = {}", orderDTO.getOrderItems());
        int orderId = orderService.createOrder(orderDTO);
        return ResponseEntity.status(201).body(new OrderCreateResponseDTO(orderId)) ;
    }
}
