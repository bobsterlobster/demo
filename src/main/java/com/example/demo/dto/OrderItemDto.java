package com.example.demo.dto;

import com.example.demo.model.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private String productName;
    private Integer quantity;
    public static OrderItemDto from(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}