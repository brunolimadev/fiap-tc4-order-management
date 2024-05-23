package br.com.fiap.ordermanagement.models.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductOutOfStockError {
    String productId;
    String productName;
}
