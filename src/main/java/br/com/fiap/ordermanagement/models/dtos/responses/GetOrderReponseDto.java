package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.dtos.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderReponseDto {

    private String clientId;
    private String orderId;
    private Double amount;
    private String currentStatus;
    private List<OrderItemDto> items;
    private String createdAt;
    private String updatedAt;

    public static GetOrderReponseDto fromEntity(Order order) {

        var orderDto = new GetOrderReponseDto();
        orderDto.setClientId(order.getClientId());
        orderDto.setOrderId(order.getId());
        orderDto.setAmount(order.getAmount());
        orderDto.setCurrentStatus(order.getCurrentStatus().name());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        orderDto.setItems(new ArrayList<>());

        for (var item : order.getItems()) {
            orderDto.getItems().add(OrderItemDto.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .productName(item.getName())
                    .price(item.getPrice())
                    .category(item.getCategory())
                    .build());
        }

        return orderDto;

    }
}
