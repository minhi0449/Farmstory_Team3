package com.farmstory.service;

import com.farmstory.dto.order.*;
import com.farmstory.dto.PageResponseDTO;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.User;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderGetResponseDTO getOrderById(int id) {
        Optional<Order> findOrder = orderRepository.findById(id);
        Order order = findOrder.orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
        return OrderGetResponseDTO.fromEntity(order);
    }

    public PageResponseDTO<OrderItemsGetResponseDTO> getOrderItems(Pageable pageable) {
        Page<OrderItem> orderItems = orderItemRepository.findAll(pageable);
        Page<OrderItemsGetResponseDTO> dtoPage = orderItems.map(OrderItemsGetResponseDTO::fromEntity);
        return PageResponseDTO.fromPage(dtoPage);
    }

    public PageResponseDTO<OrderItemsGetResponseDTO> getOrderItemsByOrderNo(int orderNo, Pageable pageable) {
        Page<OrderItem> orderItems = orderItemRepository.findByOrderNo(orderNo, pageable);
        Page<OrderItemsGetResponseDTO> dtoPage = orderItems.map(OrderItemsGetResponseDTO::fromEntity);
        return PageResponseDTO.fromPage(dtoPage);
    }

    @Transactional
    public int createOrder(OrderCreateRequestDTO orderDTO) {

        // TODO: User 정보를 가져와 Order와 연결해야함
        // User user = userRepository.findById(orderDTO.getUserId())
        //         .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 1. Order 엔티티 생성 및 저장
        Order order = OrderCreateRequestDTO.toEntity(orderDTO);
        // order.setUser(user); // User와 연결
        orderRepository.save(order);

        // 2. OrderItem 생성 및 Order와의 관계 설정
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(orderItemDTO -> {
                    OrderItem orderItem = OrderItemCreateRequestDTO.toEntity(orderItemDTO);
                    orderItem.registerOrder(order); // OrderItem에 Order 설정
                    // TODO: product도 추가
                    return orderItem;
                })
                .collect(Collectors.toList());

        // 3. OrderItem 저장
        orderItemRepository.saveAll(orderItems);

        // TODO: 주문수에 따라 product재고 감소 재고가 부족하면 Exception
        // TODO: 사용자의 포인트도 수정

        return order.getOrderNo();
    }
}
