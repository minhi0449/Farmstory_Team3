package com.farmstory.service;

import com.farmstory.dto.PageResponseDTO;
import com.farmstory.dto.order.OrderCreateRequestDTO;
import com.farmstory.dto.order.OrderGetResponseDTO;
import com.farmstory.dto.order.OrderItemCreateRequestDTO;
import com.farmstory.dto.order.OrderItemsGetResponseDTO;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.Product;
import com.farmstory.enums.PayMethod;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import com.farmstory.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OrderServiceInteTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;


    @Test
    @Transactional
    @DisplayName("주문을 생성하고 데이터베이스에 저장한다.")
    void createOrder_shouldSaveOrder() {
        // Given
        OrderCreateRequestDTO orderCreateRequestDTO = createSampleDtoData();
        productRepository.save(Product.builder().prodNo(1).stock(4).build());

        // When
        int orderId = orderService.createOrder(orderCreateRequestDTO);

        // Then
        OrderGetResponseDTO savedOrder = orderService.getOrderById(orderId);
        PageResponseDTO<OrderItemsGetResponseDTO> orderItemsByOrderNo =
                orderService.getOrderItemsByOrderNo(savedOrder.getOrderNo(), PageRequest.of(0, 10));
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getReceiver()).isEqualTo("받는사람");

        assertThat(orderItemsByOrderNo.getContent().size()).isEqualTo(2);
    }


    @Test
    @Transactional
    @DisplayName("주문 ID를 기반으로 OrderItem을 페이징하여 조회한다.")
    void getOrderItemsByOrderId_shouldReturnPagedOrderItems() {
        // Given
        int orderId = saveSampleOrderData();

        Pageable pageable = PageRequest.of(0, 1);

        // When
        PageResponseDTO<OrderItemsGetResponseDTO> result = orderService.getOrderItemsByOrderNo(orderId, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(2);

        int productId = result.getContent().get(0).getProductId();
        Product findProduct = productRepository.findById(productId);
        assertThat(productId).isEqualTo(findProduct.getProdNo());
    }

    @Test
    @Transactional
    @DisplayName("주문 ID로 주문을 조회할 때, 주문이 존재하지 않으면 예외를 발생시킨다.")
    void getOrderById_shouldThrowException_whenOrderNotFound() {
        // Given
        int invalidOrderId = 999;

        // When & Then
        assertThatThrownBy(()-> orderService.getOrderById(invalidOrderId)).isInstanceOf(IllegalArgumentException.class);
    }

    private int saveSampleOrderData() {
        Order order = Order.builder()
                .point(100)
                .receiver("받는사람")
                .receiverHp("010-1111-2222")
                .zip("우편번호")
                .addr1("주소1")
                .addr2("주소2")
                .payMethod(PayMethod.CREDIT_CARD)
                .etc("기타")
                .build();
        orderRepository.save(order);

        Product product = Product.builder()
                .build();

        productRepository.save(product);
        OrderItem orderItem1 = OrderItem.builder()
                .price(10000)
                .point(100)
                .discount(10)
                .deliveryfee(1000)
                .count(1)
                .order(order)
                .product(product)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .price(20000)
                .point(200)
                .discount(20)
                .deliveryfee(2000)
                .count(2)
                .order(order)
                .product(product)
                .build();

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);




        return order.getOrderNo();
    }

    private OrderCreateRequestDTO createSampleDtoData() {
        OrderCreateRequestDTO orderCreateRequestDTO = OrderCreateRequestDTO.builder()
                .point(100)
                .receiver("받는사람")
                .receiverHp("010-1111-2222")
                .zip("우편번호")
                .addr1("주소1")
                .addr2("주소2")
                .payMethod(PayMethod.CREDIT_CARD)
                .etc("기타")
                .build();



        OrderItemCreateRequestDTO orderItem1 = OrderItemCreateRequestDTO.builder()
                .price(10000)
                .point(100)
                .discount(10)
                .deliveryfee(1000)
                .count(1)
                .productId(1)
                .build();

        OrderItemCreateRequestDTO orderItem2 = OrderItemCreateRequestDTO.builder()
                .price(20000)
                .point(200)
                .discount(20)
                .deliveryfee(2000)
                .count(2)
                .productId(1)
                .build();

        orderCreateRequestDTO.getOrderItems().addAll(Arrays.asList(orderItem1, orderItem2));
        return orderCreateRequestDTO;
    }
}