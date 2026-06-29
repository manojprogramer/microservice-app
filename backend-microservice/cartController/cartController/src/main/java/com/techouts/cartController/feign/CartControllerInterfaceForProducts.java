package com.techouts.cartController.feign;

import com.techouts.cartController.model.Products;
import com.techouts.cartController.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("PRODUCTCONTROLLER")
public interface CartControllerInterfaceForProducts {
     @GetMapping("/products/getproducts/{id}")
     Products findById(@RequestHeader("Authorization") String token, @PathVariable("id") int id);
}
