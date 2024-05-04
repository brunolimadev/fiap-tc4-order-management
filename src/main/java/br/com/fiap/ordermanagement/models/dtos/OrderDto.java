package br.com.fiap.ordermanagement.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String clientId;
    private String orderId;
    private Double amount;
    private String currentStatus;
    private List<OrderItemDto> items;
    private String createdAt;
    private String updatedAt;



}
