package br.com.fiap.ordermanagement.models.dtos.requests;

import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderItem;
import br.com.fiap.ordermanagement.models.dtos.OrderItemDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOrderRequestDto {
    String clientId;
    List<OrderItemDto> items;


    public Order toEntity() {
        var order = Order.builder().build();
        order.setItems(new ArrayList<>());
        Double amount = 0.0;

        for (OrderItemDto item : this.items) {
            var orderItem = OrderItem.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .category(item.getCategory())
                    .price(item.getPrice())
                    .name(item.getProductName())
                    .build();
            order.getItems().add(orderItem);
            amount += item.getPrice() * item.getQuantity();
        }

        order.setAmount(amount);
        order.setClientId(clientId);

        return order;
    }
}
