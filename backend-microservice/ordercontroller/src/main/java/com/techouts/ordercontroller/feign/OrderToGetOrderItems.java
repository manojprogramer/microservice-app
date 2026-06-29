package com.techouts.ordercontroller.feign;

import com.techouts.ordercontroller.model.OrderItems;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ORDERITEMSCONTROLLER")
public interface OrderToGetOrderItems {
    @PostMapping("/orderitems/save")
     void save(@RequestHeader("Authorization") String token, @RequestBody OrderItems orderItem);

    @GetMapping("/orderitems/getorderitems/{userId}")
     ResponseEntity<List<OrderItems>> getOrders(@RequestHeader("Authorization") String token,@PathVariable("userId") long userId);


    @PostMapping("/orderitems/cancelorder/{userId}/{productId}")
    ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String token,@PathVariable("userId") long userId,@PathVariable("productId") int productId);
}
