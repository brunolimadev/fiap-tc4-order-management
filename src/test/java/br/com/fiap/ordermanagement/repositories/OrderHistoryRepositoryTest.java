package br.com.fiap.ordermanagement.repositories;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import br.com.fiap.ordermanagement.models.OrderHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderHistoryRepositoryTest {

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

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
    void shouldAllowRegisterOrderHistory() {
        // arrange
        var orderHistory = OrderHistory.builder()
                .orderId("")
                .status(StatusEnum.WAITING_SHIPMENT)
                .createdAt(LocalDateTime.now().toString())
                .description("Order changed to WAITING_SHIPMENT")
                .build();

        when(orderHistoryRepository.save(orderHistory)).thenReturn(orderHistory);

        // act
        var orderHistorySaved = orderHistoryRepository.save(orderHistory);

        // assert
        assertThat(orderHistorySaved).isNotNull();
        assertThat(orderHistorySaved.getOrderId()).isEqualTo(orderHistory.getOrderId());
        verify(orderHistoryRepository, times(1)).save(orderHistory);
    }

    @Test
    void shouldAllowFindOrderHistoryByOrderId() {
        // arrange
        var orderId = "123";
        var orderHistory = OrderHistory.builder()
                .orderId(orderId)
                .status(StatusEnum.WAITING_SHIPMENT)
                .createdAt(LocalDateTime.now().toString())
                .description("Order changed to WAITING_SHIPMENT")
                .build();

        when(orderHistoryRepository.findByOrderId(orderId)).thenReturn(Optional.of(orderHistory));

        // act
        var orderHistoryFound = orderHistoryRepository.findByOrderId(orderId);

        // assert
        assertThat(orderHistoryFound).isPresent();
        assertThat(orderHistoryFound.get().getOrderId()).isEqualTo(orderId);
        verify(orderHistoryRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void shouldAllowFindOrderHistoryByStatus() {
        // arrange
        var status = StatusEnum.WAITING_SHIPMENT;
        var orderHistory = OrderHistory.builder()
                .orderId("123")
                .status(status)
                .createdAt(LocalDateTime.now().toString())
                .description("Order changed to WAITING_SHIPMENT")
                .build();

        when(orderHistoryRepository.findByStatus(status.name())).thenReturn(List.of(orderHistory));

        // act
        var orderHistoryFound = orderHistoryRepository.findByStatus(status.name());

        // assert
        assertThat(orderHistoryFound).isNotEmpty();
        assertThat(orderHistoryFound.get(0).getStatus()).isEqualTo(status);
        verify(orderHistoryRepository, times(1)).findByStatus(status.name());
    }
}
