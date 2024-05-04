package br.com.fiap.ordermanagement.models.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponseDto {
    private String orderId;
    private String message;
    private String url;
}
