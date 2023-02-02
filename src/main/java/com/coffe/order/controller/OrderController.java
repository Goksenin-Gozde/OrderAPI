package com.coffe.order.controller;

import com.coffe.order.model.OrderDto;
import com.coffe.order.model.Cart;
import com.coffe.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Cart cart) {
        try {
            OrderDto order = orderService.createOrder(cart);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }catch (EntityNotFoundException e){
            log.error("Entity not found: " + e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something bad happened");
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

