package br.com.fiap.ordermanagement.services.impl;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrdersResponseDto;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import br.com.fiap.ordermanagement.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        var orderHistory = OrderHistory.builder()
                .orderId(orderEntity.getId())
                .status(StatusEnum.PROCESSING)
                .description("Order created")
                .createdAt(LocalDateTime.now().format(formatter).toString())
                .build();

        orderHistoryRepository.save(orderHistory);

        return CreateOrderResponseDto.fromEntity(orderEntity, "Order created successfully");
    }


    /**
     * Get all orders
     *
     * @return All orders
     */
    @Override
    public GetOrdersResponseDto getOrders() {

        var orders = orderRepository.findAll();

        return GetOrdersResponseDto.fromEntity(orders);
    }

    /**
     * Get order by client id
     *
     * @param clientId Client id
     * @return Order by client id
     */
    @Override
    public GetOrderReponseDto getOrderByOrderId(String clientId) {
        var order = orderRepository.findOrderById(clientId).orElseThrow(() -> new IllegalArgumentException("Order not found" ));
        return GetOrderReponseDto.fromEntity(order);
    }

}
