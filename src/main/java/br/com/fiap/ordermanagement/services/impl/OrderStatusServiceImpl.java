package br.com.fiap.ordermanagement.services.impl;

import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderHistoryResponseDto;
import br.com.fiap.ordermanagement.services.OrderStatusService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @KafkaListener(topics = "ECOMMERCE_ORDER_STATUS_CHANGE", groupId = "OrderManagementStatus")
    @Override
    public void changeStatus(GetOrderHistoryResponseDto orderHistoryResponseDto) {

        System.out.println("Order status changed: " + orderHistoryResponseDto.getDescription());

    }
}
