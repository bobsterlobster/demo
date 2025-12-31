package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderItemRequest;
import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User testUser;
    private Product testProduct;
    private CreateOrderRequest request;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Иван");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("iPhone 15");
        testProduct.setPrice(BigDecimal.valueOf(100000));
        testProduct.setStock(10);

        request = new CreateOrderRequest();
        request.setUserId(1L);
        request.setItems(List.of(new OrderItemRequest(1L, 2)));
    }

    @Test
    void createOrder_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        // Act
        Order result = orderService.createOrder(request);

        // Assert
        assertNotNull(result);
        assertEquals("NEW", result.getStatus());
        verify(productRepository).save(testProduct); // stock уменьшился
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_NotEnoughStock_ThrowsException() {
        // Arrange
        testProduct.setStock(1); // недостаточно товара
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
    }

    @Test
    void updateOrderStatus_Success() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus("NEW");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        orderService.updateOrderStatus(1L, "CONFIRMED");

        // Assert
        assertEquals("CONFIRMED", order.getStatus());
        verify(orderRepository).save(order);
    }
}
