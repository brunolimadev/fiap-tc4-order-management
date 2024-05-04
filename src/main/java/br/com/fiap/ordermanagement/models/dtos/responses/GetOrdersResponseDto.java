package br.com.fiap.ordermanagement.models.dtos.responses;

import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderItem;
import br.com.fiap.ordermanagement.models.dtos.OrderDto;
import br.com.fiap.ordermanagement.models.dtos.OrderItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetOrdersResponseDto {

    List<OrderDto> orders;


    public static GetOrdersResponseDto fromEntity(List<Order> orders) {

        GetOrdersResponseDto response = GetOrdersResponseDto.builder()
                .orders(new ArrayList<>())
                .build();

        for (Order order : orders) {
            OrderDto orderDto = OrderDto.builder()
                    .clientId(order.getClientId())
                    .orderId(order.getId())
                    .amount(order.getAmount())
                    .items(new ArrayList<>())
                    .build();

            for (OrderItem item : order.getItems()) {

                orderDto.getItems().add(OrderItemDto.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .productName(item.getName())
                        .price(item.getPrice())
                        .category(item.getCategory())
                        .build());

            }

            response.getOrders().add(orderDto);
        }


        return response;
    }
}
