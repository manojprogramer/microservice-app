package com.techouts.ordercontroller.feign;


import com.techouts.ordercontroller.model.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCTCONTROLLER")
public interface OrderToGetProduct {
    @GetMapping("/getproducts/{id}")
    Products getProducts(@PathVariable("id") int productId);
}
