package br.com.fiap.ordermanagement.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private String productId;
    private String name;
    private Integer quantity;
    private Double price;
    private String category;
}
