package br.com.fiap.ordermanagement.models.dtos.responses;

import java.io.Serializable;

import br.com.fiap.ordermanagement.models.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderHistoryResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private String description;
    private String status;
    private String createdAt;
    private String clientId;

    public static GetOrderHistoryResponseDto fromEntity(OrderHistory orderHistory) {
        return GetOrderHistoryResponseDto.builder()
                .orderId(orderHistory.getOrderId())
                .description(orderHistory.getDescription())
                .status(orderHistory.getStatus().name())
                .createdAt(orderHistory.getCreatedAt())
                .build();
    }
}
