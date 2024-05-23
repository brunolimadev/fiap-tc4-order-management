package br.com.fiap.ordermanagement.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fiap.ordermanagement.models.error.ErrorResponse;
import br.com.fiap.ordermanagement.models.error.ProductOutOfStockErrorResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<?> handleProductOutOfStockException(ProductOutOfStockException e) {
        ProductOutOfStockErrorResponse errorResponse = new ProductOutOfStockErrorResponse("Product out of stock",
                e.getMessage(), e.getProducts());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid request", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}