package com.farmstory.service;

import com.farmstory.dto.PageResponseDTO;
import com.farmstory.dto.order.*;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.Product;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import com.farmstory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        // 초기화 작업이 필요한 경우 여기에 작성
    }

    // 1. getOrderById 테스트
    @Test
    @DisplayName("존재하는 주문 ID로 주문 조회에 성공해야 한다.")
    void getOrderById_shouldReturnOrderWhenIdExists() {
        // Given
        Order order = Order.builder().orderNo(1).build();
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));

        // When
        OrderGetResponseDTO result = orderService.getOrderById(1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderNo()).isEqualTo(1);
        verify(orderRepository, times(1)).findById(anyInt());
    }

    // 2. getOrderById 테스트 (주문이 존재하지 않을 때)
    @Test
    @DisplayName("존재하지 않는 주문 ID로 조회 시 예외가 발생해야 한다.")
    void getOrderById_shouldThrowExceptionWhenIdDoesNotExist() {
        // Given
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(1));
    }

    // 3. getOrderItems 테스트
    @Test
    @DisplayName("페이징을 사용하여 모든 주문 항목을 조회한다.")
    void getOrderItems_shouldReturnPagedOrderItems() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Product product = Product.builder().prodNo(1).build();
        List<OrderItem> orderItems = Collections.singletonList(OrderItem.builder().orderItemNo(1).product(product).build());
        Page<OrderItem> page = new PageImpl<>(orderItems);

        when(orderItemRepository.findAll(pageable)).thenReturn(page);

        // When
        PageResponseDTO<OrderItemsGetResponseDTO> result = orderService.getOrderItems(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(1);
        verify(orderItemRepository, times(1)).findAll(pageable);
    }

    // 4. getOrderItemsByOrderId 테스트
    @Test
    @DisplayName("주문 ID와 페이징을 사용하여 주문 항목을 조회한다.")
    void getOrderItemsByOrderId_shouldReturnPagedOrderItems() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Product product = Product.builder().prodNo(1).build();
        List<OrderItem> orderItems = Collections.singletonList(OrderItem.builder().orderItemNo(1).product(product).build());
        Page<OrderItem> page = new PageImpl<>(orderItems);
        when(orderItemRepository.findByOrderNo(anyInt(), eq(pageable))).thenReturn(page);

        // When
        PageResponseDTO<OrderItemsGetResponseDTO> result = orderService.getOrderItemsByOrderNo(1, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(1);
        verify(orderItemRepository, times(1)).findByOrderNo(anyInt(), eq(pageable));
    }

    // 5. createOrder 테스트
    @Test
    @DisplayName("새로운 주문을 생성할 때, 올바른 주문 ID를 반환한다.")
    void createOrder_shouldReturnOrderId() {
        // Given
        Product product = Product.builder()
                .prodNo(1)
                .build();

        List<OrderItemCreateRequestDTO> orderItems = new ArrayList<>();

        OrderItemCreateRequestDTO orderItem = OrderItemCreateRequestDTO.builder().productId(product.getProdNo()).build();
        orderItems.add(orderItem);

        OrderCreateRequestDTO orderDTO = OrderCreateRequestDTO.builder()
                .orderItems(orderItems)
                .build();

        // Mocking orderRepository.save()
        Order order = Order.builder()
                .orderNo(1) // orderNo를 1로 설정
                .build();

        OrderItem orderItemEntity = orderItem.toEntity();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItemEntity);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));

        // When
        int result = orderService.createOrder(orderDTO);

        // Then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
        verify(productRepository, times(1)).findById(any(Integer.class));
    }
}