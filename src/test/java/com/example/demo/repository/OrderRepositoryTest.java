package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(false)
    void saveOrderWithUser() {
        // Arrange
        User user = new User();
        user.setName("Тестовый пользователь");
        user = userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus("NEW");

        // Act
        Order savedOrder = orderRepository.save(order);

        // Assert
        assertThat(savedOrder.getId()).isGreaterThan(0);
        assertThat(savedOrder.getUser()).isEqualTo(user);
    }

    @Test
    void findAllOrders() {
        // Act
        List<Order> orders = orderRepository.findAll();

        // Assert
        assertThat(orders).isNotNull();
    }
}
