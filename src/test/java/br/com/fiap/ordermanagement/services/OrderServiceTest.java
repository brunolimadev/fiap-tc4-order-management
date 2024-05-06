package br.com.fiap.ordermanagement.services;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderHistory;
import br.com.fiap.ordermanagement.models.OrderItem;
import br.com.fiap.ordermanagement.models.dtos.OrderItemDto;
import br.com.fiap.ordermanagement.models.dtos.requests.ChangeStatusRequestDto;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.repositories.OrderHistoryRepository;
import br.com.fiap.ordermanagement.repositories.OrderRepository;
import br.com.fiap.ordermanagement.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    private OrderService orderService;

    AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository, orderHistoryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldRegisterOrder() {
        // arrange
        var orderDto = new CreateOrderRequestDto();
        orderDto.setClientId("123");
        orderDto.setItems(List.of(OrderItemDto.builder()
                .category("category")
                .price(10.0)
                .productId("123")
                .quantity(1)
                .build()));

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // act
        var orderSaved = orderService.createOrder(orderDto);

        // assert
        assertThat(orderSaved).isNotNull();
        verify(orderRepository, times(1)).save(orderDto.toEntity());
    }

    @Test
    void shouldUpdateOrderStatus() {
        // arrange
        var order = Order.builder()
                .id("1233")
                .clientId("123")
                .currentStatus(StatusEnum.WAITING_PAYMENT)
                .build();

        var orderHistory = OrderHistory.builder()
                .orderId("123")
                .status(StatusEnum.WAITING_SHIPMENT)
                .createdAt(LocalDateTime.now().toString())
                .description("Order changed to WAITING_SHIPMENT")
                .build();

        when(orderHistoryRepository.findByOrderId("123")).thenReturn(Optional.of(orderHistory));
        when(orderHistoryRepository.save(any(OrderHistory.class))).thenReturn(orderHistory);
        when(orderRepository.findOrderById("123")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // act
        var orderUpdated = orderService.changeStatus("123", ChangeStatusRequestDto.builder()
                .status(StatusEnum.WAITING_SHIPMENT.ordinal())
                .description("description")
                .build());

        // assert
        assertThat(orderUpdated).isNotNull();
        assertThat(orderUpdated.getMessage()).contains("Status has been changed for order");
        verify(orderHistoryRepository, times(1)).findByOrderId("123");
        verify(orderRepository, times(1)).findOrderById("123");
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldGetOrderById() {
        // arrange
        var orderItem = OrderItem.builder()
                .productId("123")
                .quantity(1)
                .price(1350.00)
                .category("Eletronics")
                .name("TV 60 inches 4k")
                .build();

        var order = Order.builder()
                .amount(100.00)
                .clientId("123")
                .createdAt("2021-10-10")
                .updatedAt(null)
                .currentStatus(StatusEnum.PROCESSING)
                .items(Arrays.asList(orderItem))
                .build();

        when(orderRepository.findOrderById("123")).thenReturn(Optional.of(order));

        // act
        var orderFound = orderService.getOrderByOrderId("123");

        // assert
        assertThat(orderFound).isNotNull();
        verify(orderRepository, times(1)).findOrderById("123");
    }

    @Test
    void shouldGetAllOrders() {
        // arrange
        var orderItem = OrderItem.builder()
                .productId("123")
                .quantity(1)
                .price(1350.00)
                .category("Eletronics")
                .name("TV 60 inches 4k")
                .build();

        var order = Order.builder()
                .amount(100.00)
                .clientId("123")
                .createdAt("2021-10-10")
                .updatedAt(null)
                .currentStatus(StatusEnum.PROCESSING)
                .items(Arrays.asList(orderItem))
                .build();

        when(orderRepository.findAll()).thenReturn(List.of(order));

        // act
        var orders = orderService.getOrders();

        // assert
        assertThat(orders.getOrders()).hasSize(1);
        verify(orderRepository, times(1)).findAll();
    }
}
