package com.techouts.ordercontroller.controller;


import com.techouts.ordercontroller.model.OrderItems;
import com.techouts.ordercontroller.model.Orders;
import com.techouts.ordercontroller.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    private Orders placeOrder(@RequestHeader("Authorization") String token,@RequestBody Orders order) {
        return orderService.placeOrder(token,order);
    }
    @GetMapping("/getorders")
    private ResponseEntity<List<OrderItems>> getOrders(@RequestHeader("Authorization") String token) {
        System.out.println("Entering into the controller");
        return orderService.getOrders(token);
    }
    @PostMapping("/cancelorder/{productId}")
    private ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String token,@PathVariable int productId)
    {
        System.out.println("Entering into the controller");
        return orderService.cancelOrder(token,productId);
    }

}
