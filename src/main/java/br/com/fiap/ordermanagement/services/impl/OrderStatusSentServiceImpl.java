package br.com.fiap.ordermanagement.services.impl;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderHistoryResponseDto;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import br.com.fiap.ordermanagement.services.OrderStatusService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class OrderStatusSentServiceImpl implements OrderStatusService {

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderStatusSentServiceImpl(OrderRepository orderRepository, OrderHistoryRepository orderHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @KafkaListener(topics = "ECOMMERCE_ORDER_SENT", groupId = "OrderManagementStatus")
    @Override
    public void changeStatus(String message) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        var orderHistoryResponseDto = new Gson().fromJson(message, GetOrderHistoryResponseDto.class);

        var order = orderRepository.findById(orderHistoryResponseDto.getOrderId()).get();

        var orderHistory = OrderHistory.builder()
                .description("Order changed to SENT")
                .status(StatusEnum.SENT)
                .createdAt(LocalDateTime.now().format(formatter).toString())
                .orderId(orderHistoryResponseDto.getOrderId()).build();

        order.setUpdatedAt(LocalDateTime.now().format(formatter).toString());
        order.setCurrentStatus(StatusEnum.SENT);

        orderRepository.save(order);

        orderHistoryRepository.save(orderHistory);

    }
}
