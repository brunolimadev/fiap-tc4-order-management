package br.com.fiap.ordermanagement.controllers;

import br.com.fiap.ordermanagement.controllers.exceptions.ProductOutOfStockException;
import br.com.fiap.ordermanagement.models.dtos.requests.ChangeStatusRequestDto;
import br.com.fiap.ordermanagement.models.dtos.requests.CreateOrderRequestDto;
import br.com.fiap.ordermanagement.models.dtos.responses.ChangeStatusResponseDto;
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
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    /**
     * Get order by order id
     *
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderReponseDto> getOrderByClientId(@PathVariable String orderId) {
        var order = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    /**
     * Create a new order
     *
     * @param body
     * @return
     * @throws ProductOutOfStockException
     */
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto body)
            throws ProductOutOfStockException {
        var order = orderService.createOrder(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * Change order status
     *
     * @param orderId
     * @param status
     * @return
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ChangeStatusResponseDto> changeStatus(@PathVariable String orderId,
            @RequestBody ChangeStatusRequestDto body) {
        var order = orderService.changeStatus(orderId, body);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

}
