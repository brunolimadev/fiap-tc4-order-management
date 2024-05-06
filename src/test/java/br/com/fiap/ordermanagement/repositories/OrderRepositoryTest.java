package br.com.fiap.ordermanagement.repositories;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.Order;
import br.com.fiap.ordermanagement.models.OrderItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldRegisterOrder() {

        var orderItem = OrderItem.builder()
                .productId("123")
                .quantity(1)
                .price(1350.00)
                .category("Eletronics")
                .name("TV 60 inches 4k")
                .build();

        // arrange
        var order = Order.builder()
                .amount(100.00)
                .clientId("123")
                .createdAt("2021-10-10")
                .updatedAt(null)
                .currentStatus(StatusEnum.PROCESSING)
                .items(Arrays.asList(orderItem))
                .build();

        when(orderRepository.save(order)).thenReturn(order);

        // act
        var savedOrder = orderRepository.save(order);

        // assert
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getAmount()).isEqualTo(order.getAmount());
        verify(orderRepository, times(1)).save(order);
    }


    @Test
    void shouldSearchOrderById(){

            var orderItem = OrderItem.builder()
                    .productId("123")
                    .quantity(1)
                    .price(1350.00)
                    .category("Eletronics")
                    .name("TV 60 inches 4k")
                    .build();

            // arrange
            var order = Order.builder()
                    .amount(100.00)
                    .clientId("123")
                    .createdAt("2021-10-10")
                    .updatedAt(null)
                    .currentStatus(StatusEnum.PROCESSING)
                    .items(Arrays.asList(orderItem))
                    .build();

            var orderId = "123";

            when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

            // act
            var foundOrder = orderRepository.findById(orderId);

            // assert
            assertThat(foundOrder).isPresent().containsSame(order);
    }

    @Test
    void shouldSearchOrderByStatus(){

        var status = "PROCESSING";

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

        when(orderRepository.findOrdersByCurrentStatus(status)).thenReturn(Arrays.asList(order));

        var orders = orderRepository.findOrdersByCurrentStatus(status);

        assertThat(orders).isNotNull().isNotEmpty().hasSize(1);
    }


}
