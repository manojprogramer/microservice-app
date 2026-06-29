package com.techouts.cartController.controller;


import com.techouts.cartController.model.Cart;
import com.techouts.cartController.model.Products;
import com.techouts.cartController.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"})
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addtocart/{id}")
    private Products cartProduct(@RequestHeader("Authorization") String token,@PathVariable int id) {
        return cartService.saveCart(token, id);
    }

    @GetMapping("/getcart")
    private ResponseEntity<List<Cart>> getCart(@RequestHeader("Authorization") String token) {
        return cartService.getCart(token);
    }
    @PostMapping("/increment/{productId}")
    private ResponseEntity<String> incrementProductCount(@PathVariable("productId") int productId,@RequestHeader("Authorization") String token) {
        return cartService.incrementProductCount(token,productId);
    }
    @PostMapping("/decrement/{productId}")
    private ResponseEntity<String> decrementProductCount(@RequestHeader("Authorization") String token,@PathVariable int productId) {
        return cartService.decrementProductId(token,productId);
    }
    @PostMapping("/delete/{productId}")
    private ResponseEntity<String> deleteProduct(@RequestHeader("Authorization") String token,@PathVariable("productId") int productId){
        return cartService.deleteProduct(token,productId);
    }
    @PostMapping("/deletecart/{userid}")
    private ResponseEntity<String> deleteCart(@RequestHeader("Authorization") String token,@PathVariable("userid") int user_id) {
        return cartService.deleteCart(user_id);
    }

}
