package br.com.fiap.ordermanagement.services;

import br.com.fiap.ordermanagement.models.dtos.requests.ChangeStatusRequestDto;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.ChangeStatusResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrdersResponseDto;

public interface OrderService {

    /**
     * Create a new order
     * @param order
     * @return
     */
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto order);

    /**
     * Get all orders
     * @return
     */
    public GetOrdersResponseDto getOrders();

    /**
     * Get order by client id
     * @param clientId
     * @return
     */
    public GetOrderReponseDto getOrderByOrderId(String clientId);

    /**
     * Change order status
     * @param orderId
     * @param status
     * @return
     */
    public ChangeStatusResponseDto changeStatus(String orderId, ChangeStatusRequestDto request);


}
