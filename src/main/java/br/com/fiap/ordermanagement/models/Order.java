package br.com.fiap.ordermanagement.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
@Data
@Builder
public class Order {

    @Id
    private String id;

    private Double amount;

    private String clientId;

    private List<OrderItem> items;


}
