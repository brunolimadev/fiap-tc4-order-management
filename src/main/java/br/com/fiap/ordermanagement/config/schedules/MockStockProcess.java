package br.com.fiap.ordermanagement.config.schedules;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class MockStockProcess {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderRepository orderRepository;

    public MockStockProcess(OrderHistoryRepository orderHistoryRepository, OrderRepository orderRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Check status in processing.
     */
    @Scheduled(fixedRate = 4000, initialDelay = 2000)
    public void checkStatusInProcessing() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        List<Order> orders = orderRepository.findOrdersByCurrentStatus(StatusEnum.PROCESSING.name());

        if (orders.isEmpty()) return;

        List<OrderHistory> newOrders = new ArrayList<>();

        orders.forEach(order -> {
            var orderHistory = OrderHistory.builder()
                    .description("Order changed to WAITING_PAYMENT")
                    .status(StatusEnum.WAITING_PAYMENT)
                    .createdAt(LocalDateTime.now().format(formatter).toString())
                    .orderId(order.getId()).build();

            log.info("Order {} changed to WAITING_PAYMENT", orderHistory.getOrderId());

            order.setUpdatedAt(LocalDateTime.now().format(formatter).toString());
            order.setCurrentStatus(StatusEnum.WAITING_PAYMENT);

            newOrders.add(orderHistory);
        });

        var result = orderHistoryRepository.saveAll(newOrders);
        orderRepository.saveAll(orders);

        log.info("Orders changed to WAITING_PAYMENT: {}", result.size());

    }

}
