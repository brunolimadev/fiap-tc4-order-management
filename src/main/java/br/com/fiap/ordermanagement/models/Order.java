package br.com.fiap.ordermanagement.models;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private StatusEnum currentStatus;

    private List<OrderItem> items;

    private String createdAt;

    private String updatedAt;


}
