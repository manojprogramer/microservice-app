package com.techouts.orderItemsController.controller;

import com.techouts.orderItemsController.model.OrderItems;
import com.techouts.orderItemsController.service.OrderItemService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {

    @Autowired
    OrderItemService orderItemService;

    @PostMapping("/save")
    public OrderItems save(@RequestHeader("Authorization") String token,@RequestBody OrderItems orderItems) {
        System.out.println("Order controller is entering into the orderItems");
        return orderItemService.save(orderItems);
    }

    @GetMapping("/getorderitems/{userId}")
    public List<OrderItems> getOrderItems(@RequestHeader("Authorization") String token,@PathVariable("userId") long id) {
        System.out.println("Entering into getOrder controller");
        return orderItemService.getOrderItems(id);
    }
    @PostMapping("/cancelorder/{userId}/{productId}")
    public String cancelOrder(@PathVariable("userId")long userId,@PathVariable("productId") int productId){
        return orderItemService.cancelOrder(userId,productId);
    }
}
