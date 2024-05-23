package br.com.fiap.ordermanagement.models.error;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductOutOfStockErrorResponse extends ErrorResponse {

    List<ProductOutOfStockError> products;

    public ProductOutOfStockErrorResponse(String title, String message, List<ProductOutOfStockError> products) {
        super(title, message);
        this.products = products;
    }

}
