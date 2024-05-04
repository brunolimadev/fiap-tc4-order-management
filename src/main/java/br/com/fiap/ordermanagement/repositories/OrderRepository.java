package br.com.fiap.ordermanagement.repositories;

import br.com.fiap.ordermanagement.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findOrderById(String orderId);
}
