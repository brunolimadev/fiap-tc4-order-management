package br.com.fiap.ordermanagement.services.impl;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.dtos.OrderItemDto;
import br.com.fiap.ordermanagement.models.dtos.requests.ChangeStatusRequestDto;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.ChangeStatusResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrdersResponseDto;
import br.com.fiap.ordermanagement.models.product.request.ProductRequest;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import br.com.fiap.ordermanagement.services.OrderService;
import br.com.fiap.ordermanagement.services.ProductStockService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final ProductStockService productStockService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderHistoryRepository orderHistoryRepository,
            ProductStockService productStockService) {
        this.orderRepository = orderRepository;
        this.orderHistoryRepository = orderHistoryRepository;
        this.productStockService = productStockService;
    }

    /**
     * Create a new order
     *
     * @param order Order to be created
     * @return Order created
     */
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto order) {

        for (OrderItemDto item : order.getItems()) {
            checkStock(item);
        }

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
     * @param orderId Client id
     * @return Order by client id
     */
    @Override
    public GetOrderReponseDto getOrderByOrderId(String orderId) {
        var order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return GetOrderReponseDto.fromEntity(order);
    }

    /**
     * Change order status
     *
     * @param orderId Order id
     * @param body    Body with new status
     * @return Order status changed
     */
    @Override
    public ChangeStatusResponseDto changeStatus(String orderId, ChangeStatusRequestDto body) {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        var currentOrder = orderHistoryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var orderHistory = OrderHistory.builder()
                .orderId(currentOrder.getId())
                .status(StatusEnum.values()[body.getStatus()])
                .description(body.getDescription())
                .createdAt(LocalDateTime.now().toString())
                .build();

        var result = orderHistoryRepository.save(orderHistory);

        var order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setCurrentStatus(StatusEnum.values()[body.getStatus()]);
        order.setUpdatedAt(LocalDateTime.now().format(formatter).toString());

        orderRepository.save(order);

        return ChangeStatusResponseDto.fromEntity(result);
    }

    private void checkStock(OrderItemDto item) {
        var response = productStockService.checkStock(Integer.parseInt(item.getProductId()));

        if (Integer.parseInt(response.getStoreQuantity()) < item.getQuantity()) {
            throw new IllegalArgumentException("Product out of stock");
        }

        var currentStock = Integer.parseInt(response.getStoreQuantity()) - item.getQuantity();

        var productRequest = ProductRequest.builder()
                .id(Integer.parseInt(item.getProductId()))
                .description(response.getDescription())
                .price(response.getPrice())
                .storeQuantity(String.valueOf(currentStock))
                .build();

        updateStock(productRequest);
    }

    private void updateStock(ProductRequest item) {
        productStockService.updateStock(item);
    }

}
