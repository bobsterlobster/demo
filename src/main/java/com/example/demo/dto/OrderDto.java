package com.example.demo.dto;

import com.example.demo.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String userName;
    private String status;
    private List<OrderItemDto> items;

    public static OrderDto from(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserName(order.getUser().getName());
        dto.setStatus(order.getStatus());
        dto.setItems(order.getItems().stream().map(OrderItemDto::from).toList());
        return dto;
    }
}