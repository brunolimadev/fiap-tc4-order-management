package br.com.fiap.ordermanagement.controllers;

import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrdersResponseDto;
import br.com.fiap.ordermanagement.services.OrderService;
import br.com.fiap.ordermanagement.services.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderManagementController {

    private final OrderService orderService;

    public OrderManagementController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    /**
     * Get all orders
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<GetOrdersResponseDto> getOrders() {
        var orders = orderService.getOrders();
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    /**
     * Get order by client id
     *
     * @param clientId
     * @return
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<GetOrderReponseDto> getOrderByClientId(@PathVariable String clientId) {
        var order = orderService.getOrderByClientId(clientId);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    /**
     * Create a new order
     *
     * @param body
     * @return
     */
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto body) {
        var order = orderService.createOrder(body);
        return new ResponseEntity(order, HttpStatus.CREATED);
    }

}
