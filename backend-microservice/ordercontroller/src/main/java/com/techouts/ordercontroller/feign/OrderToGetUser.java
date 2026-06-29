package com.techouts.ordercontroller.feign;


import com.techouts.ordercontroller.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("LOGINCONTROLLER")
public interface OrderToGetUser {
        @GetMapping("/user/getuser")
        Users findByEmail(@RequestHeader("Authorization") String token,@RequestParam String email);
}
