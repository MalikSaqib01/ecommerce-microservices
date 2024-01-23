package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.service.OrderService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController{

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        log.info("placing order....");
       return orderService.createOrder(orderRequest);
    }


//    @GetMapping
//    public OrderResponse getAllOrders()
//    {
//
//    }

}
