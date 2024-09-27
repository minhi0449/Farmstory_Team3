package com.farmstory.repository;

import com.farmstory.config.QueryDslConfig;
import com.farmstory.entity.Order;
import com.farmstory.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(QueryDslConfig.class)
class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("OrderNo를 기반으로 OrderItem을 페이징하여 조회한다.")
    @Transactional
    void findByOrderNo_shouldReturnPagedOrderItems() {
        // Given
        Order order = Order.builder().orderNo(1).build();
        orderRepository.save(order);

        OrderItem orderItem1 = OrderItem.builder().order(order).price(100).count(2).build();
        OrderItem orderItem2 = OrderItem.builder().order(order).price(200).count(1).build();
        OrderItem orderItem3 = OrderItem.builder().order(order).price(300).count(3).build();
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        orderItemRepository.save(orderItem3);
        orderItemRepository.flush();
        Pageable pageable = PageRequest.of(0, 2); // 첫 번째 페이지, 크기 2

        // When
        Page<OrderItem> result = orderItemRepository.findByOrderNo(1, pageable);
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(2); // 페이지당 2개의 항목을 기대
        assertThat(result.getTotalElements()).isEqualTo(3); // 총 3개의 항목을 기대
        assertThat(result.getContent().get(0).getPrice()).isEqualTo(100);
        assertThat(result.getContent().get(1).getPrice()).isEqualTo(200);
    }
}