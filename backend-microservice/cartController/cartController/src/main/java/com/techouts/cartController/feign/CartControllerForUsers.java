package com.techouts.cartController.feign;

import com.techouts.cartController.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("LOGINCONTROLLER")
public interface  CartControllerForUsers {
    @GetMapping("/user/getuser")
    Users findByEmail(@RequestHeader("Authorization") String token, @RequestParam("email") String email);
}
