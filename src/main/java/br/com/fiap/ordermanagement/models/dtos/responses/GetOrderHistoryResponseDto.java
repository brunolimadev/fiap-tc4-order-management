package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderHistoryResponseDto {
    private String orderId;
    private String description;
    private String status;
    private String createdAt;


    public static GetOrderHistoryResponseDto fromEntity(OrderHistory orderHistory) {
        return GetOrderHistoryResponseDto.builder()
                .orderId(orderHistory.getOrderId())
                .description(orderHistory.getDescription())
                .status(orderHistory.getStatus().name())
                .createdAt(orderHistory.getCreatedAt())
                .build();
    }
}
