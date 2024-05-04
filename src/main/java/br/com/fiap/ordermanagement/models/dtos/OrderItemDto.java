package br.com.fiap.ordermanagement.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String category;
}
