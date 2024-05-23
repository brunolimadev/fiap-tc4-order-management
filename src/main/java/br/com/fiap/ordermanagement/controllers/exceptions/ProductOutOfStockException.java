package br.com.fiap.ordermanagement.controllers.exceptions;

import java.util.List;

import br.com.fiap.ordermanagement.models.error.ProductOutOfStockError;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductOutOfStockException extends Exception {

    List<ProductOutOfStockError> products;

    public ProductOutOfStockException(String message, List<ProductOutOfStockError> products) {
        super(message);
        this.products = products;
    }

}