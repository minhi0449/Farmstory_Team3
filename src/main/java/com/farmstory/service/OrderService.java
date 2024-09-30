package com.farmstory.service;

import com.farmstory.dto.order.*;
import com.farmstory.dto.PageResponseDTO;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.Product;
import com.farmstory.entity.User;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

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
        Order order = orderDTO.toEntity();
        // order.setUser(user); // User와 연결
        orderRepository.save(order);



        // 2. 주문의 OrderItem을 반복
        orderDTO.getOrderItems().forEach(orderItemDto -> {
            // orderItemDto의 productId로 제품을 가져온다\
            Product product = productRepository.findById(Integer.valueOf(orderItemDto.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 product가 없습니다."));

            // 제품의 재고를 줄인다
            product.decreaseStock(orderItemDto.getCount());


            // 주문 아이템을 엔티티로 변환 후 주문과 제품의 관계를 연결한다.
            OrderItem orderItem = orderItemDto.toEntity();
            orderItem.registerOrder(order);
            orderItem.registerProduct(product);

            // 주문 아이템을 생성
            orderItemRepository.save(orderItem);
        });


        // TODO: 사용자의 포인트도 수정

        return order.getOrderNo();
    }
}
