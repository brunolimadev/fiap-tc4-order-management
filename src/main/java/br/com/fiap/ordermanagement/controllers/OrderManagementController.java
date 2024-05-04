package br.com.fiap.ordermanagement.controllers;

import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.CreateOrderResponseDto;
import br.com.fiap.ordermanagement.services.OrderService;
import br.com.fiap.ordermanagement.services.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderManagementController {

    private final OrderService orderService;

    public OrderManagementController(OrderServiceImpl orderService) {
        this.orderService = orderService;
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
