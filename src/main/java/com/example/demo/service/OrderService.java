package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service  // ✅ Spring найдет этот бин автоматически
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUser(userRepo.findById(request.getUserId()).orElseThrow());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");

        // Проверка stock и создание OrderItem
        for (var itemReq : request.getItems()) {
            Product product = productRepo.findById(itemReq.getProductId()).orElseThrow();
            if (product.getStock() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Недостаточно товара");
            }
            product.setStock(product.getStock() - itemReq.getQuantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            order.getItems().add(item);
        }
        return orderRepo.save(order);
    }

    public void updateOrderStatus(Long id, String status) {
        Order order = orderRepo.findById(id).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
