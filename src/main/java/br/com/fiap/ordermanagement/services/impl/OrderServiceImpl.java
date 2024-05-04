package br.com.fiap.ordermanagement.services.impl;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import br.com.fiap.ordermanagement.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderHistoryRepository orderHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    /**
     * Create a new order
     *
     * @param order Order to be created
     * @return Order created
     */
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto order) {

        var orderEntity = orderRepository.save(order.toEntity());

        var orderHistory = OrderHistory.builder()
                .orderId(orderEntity.getId())
                .status(StatusEnum.PROCESSING)
                .description("Order created")
                .build();

        orderHistoryRepository.save(orderHistory);

        return CreateOrderResponseDto.builder()
                .orderId(orderEntity.getId())
                .message("Order created successfully")
                .url("/orders/" + orderEntity.getId())
                .build();
    }

}
