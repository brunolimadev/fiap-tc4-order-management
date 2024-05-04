package br.com.fiap.ordermanagement.repositories;

import br.com.fiap.ordermanagement.models.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String> {
}
