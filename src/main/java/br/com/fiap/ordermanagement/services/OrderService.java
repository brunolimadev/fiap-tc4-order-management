package br.com.fiap.ordermanagement.services;

import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;

public interface OrderService {

    /**
     * Create a new order
     * @param order
     * @return
     */
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto order);

}
