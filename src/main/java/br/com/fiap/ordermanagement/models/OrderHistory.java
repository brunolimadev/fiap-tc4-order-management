package br.com.fiap.ordermanagement.models;

import br.com.fiap.ordermanagement.enumerators.StatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders_history")
@Data
@Builder
public class OrderHistory {

    private String id;

    private String orderId;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreatedDate
    private String createdAt;



}
