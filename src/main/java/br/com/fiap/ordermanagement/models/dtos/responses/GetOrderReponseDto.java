package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.dtos.OrderDto;
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
    private List<OrderItemDto> items;

    public static GetOrderReponseDto fromEntity(Order order) {

        var orderDto = new GetOrderReponseDto();
        orderDto.setClientId(order.getClientId());
        orderDto.setOrderId(order.getId());
        orderDto.setAmount(order.getAmount());
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
