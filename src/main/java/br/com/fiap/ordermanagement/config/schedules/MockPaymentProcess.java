package br.com.fiap.ordermanagement.config.schedules;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderHistoryResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
@EnableScheduling
@Slf4j
public class MockPaymentProcess {

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final KafkaTemplate<String, GetOrderHistoryResponseDto> kafkaTemplate;

    @Value(value = "${kafka.topic.order-status-change}")
    private String topic;

    public MockPaymentProcess(OrderHistoryRepository orderHistoryRepository, OrderRepository orderRepository, KafkaTemplate<String, GetOrderHistoryResponseDto> kafkaTemplate) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Check status waiting payment.
     */
    @Scheduled(fixedRate = 4000, initialDelay = 2000)
    public void checkStatusWaitingPayment() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        List<Order> orders = orderRepository.findOrdersByCurrentStatus(StatusEnum.WAITING_PAYMENT.name());

        if (orders.isEmpty()) return;

        List<OrderHistory> newOrders = new ArrayList<>();

        orders.forEach(order -> {
            var orderHistory = OrderHistory.builder()
                    .description("Order changed to WAITING_SHIPMENT")
                    .status(StatusEnum.WAITING_SHIPMENT)
                    .createdAt(LocalDateTime.now().format(formatter).toString())
                    .orderId(order.getId()).build();

            log.info("Order {} changed to WAITING_SHIPMENT", orderHistory.getOrderId());

            order.setUpdatedAt(LocalDateTime.now().format(formatter).toString());
            order.setCurrentStatus(StatusEnum.WAITING_SHIPMENT);

            sendTopic(GetOrderHistoryResponseDto.fromEntity(orderHistory));

            newOrders.add(orderHistory);
        });

        var result = orderHistoryRepository.saveAll(newOrders);
        orderRepository.saveAll(orders);

        log.info("Orders changed to WAITING_SHIPMENT: {}", result.size());
    }

    private void sendTopic(GetOrderHistoryResponseDto order) {
        var record = new ProducerRecord<>(topic, order.getOrderId(), order);
        kafkaTemplate.send(record);
//        log.info("Order {} sent to topic {} and partition {}", order.getOrderId(), result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
    }
}
