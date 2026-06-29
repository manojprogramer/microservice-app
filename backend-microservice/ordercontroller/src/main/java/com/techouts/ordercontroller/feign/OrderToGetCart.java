package com.techouts.ordercontroller.feign;

import com.techouts.ordercontroller.model.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("CARTCONTROLLER")
public interface OrderToGetCart {
    @GetMapping("/cart/getcart")
    List<Cart> getCart(@RequestHeader("Authorization") String token, @RequestParam long user_id);


    @PostMapping("/cart/deletecart/{userId}")
    void deleteCart(@RequestHeader("Authorization") String token,@PathVariable("userId") long userId);
}
