package br.com.fiap.ordermanagement.models.dtos;

import lombok.Data;

@Data
public class OrderItemDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String category;
}
