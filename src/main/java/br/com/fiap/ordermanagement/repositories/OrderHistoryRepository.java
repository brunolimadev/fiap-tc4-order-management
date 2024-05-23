package br.com.fiap.ordermanagement.repositories;

import br.com.fiap.ordermanagement.models.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String> {

    public Optional<OrderHistory> findByOrderId(String orderId);

    public List<OrderHistory> findByStatus(String status);
}
