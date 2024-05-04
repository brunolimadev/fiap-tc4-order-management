package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponseDto {
    private String orderId;
    private String message;
    private String currentStatus;
    private String url;

    public static CreateOrderResponseDto fromEntity(Order order, String message) {
        return CreateOrderResponseDto.builder()
                .orderId(order.getId())
                .message(message)
                .currentStatus(order.getCurrentStatus().name())
                .url("/orders/" + order.getId())
                .build();
    }
}
