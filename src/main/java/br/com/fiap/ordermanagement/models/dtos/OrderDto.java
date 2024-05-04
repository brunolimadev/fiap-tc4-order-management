package br.com.fiap.ordermanagement.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDto {

    private String clientId;
    private String orderId;
    private Double amount;
    private List<OrderItemDto> items;

}
