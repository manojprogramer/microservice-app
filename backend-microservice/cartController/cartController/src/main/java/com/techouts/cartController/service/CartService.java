package com.techouts.cartController.service;


import com.netflix.discovery.converters.Auto;
import com.techouts.cartController.dao.CartRepo;
import com.techouts.cartController.feign.CartControllerForUsers;
import com.techouts.cartController.feign.CartControllerInterfaceForProducts;
import com.techouts.cartController.model.Cart;
import com.techouts.cartController.model.Products;
import com.techouts.cartController.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepo cartRepo;
    Cart c;

    @Autowired
    CartControllerForUsers cartControllerForUsers;

    @Autowired
    CartControllerInterfaceForProducts cartControllerInterfaceForProducts;

    public Products saveCart(String token, int id) {
        Products product = cartControllerInterfaceForProducts.findById(token,id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert  authentication != null;
        String email = authentication.getName();
        Users user = cartControllerForUsers.findByEmail(token,email);
        Cart c = cartRepo.findByUserAndProductName(user.getId(),product.getId());
        System.out.println(c);
        if(c != null) {
            c.setQuantity(c.getQuantity()+1);
            cartRepo.save(c);
        }
        else {
            Cart cart  = new Cart();
            cart.setQuantity(1);
            cart.setProductId(product.getId());
            cart.setImageUrl(product.getImageUrl());
            cart.setProductName(product.getProductName());
            cart.setProductPrice(product.getPrice());
            cart.setUserId(user.getId());

            cartRepo.save(cart);
        }
        return product;
    }
    public ResponseEntity<List<Cart>> getCart(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String email = authentication.getName();
        Users user = cartControllerForUsers.findByEmail(token,email);
        List<Cart> cartList =  cartRepo.findByUserId(user.getId());
        return ResponseEntity.ok(cartList);
    }
    public ResponseEntity<String> incrementProductCount(String token,int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users user = cartControllerForUsers.findByEmail(token,email);
        List<Cart> cart = cartRepo.findByUserId(user.getId());

        if(cart != null)
        {
            for(Cart c : cart) {

                System.out.println(c.getProductId() +"Name "+c.getProductName());
                if (c.getProductId() == id) {
                    c.setQuantity(c.getQuantity() + 1);
                    cartRepo.save(c);
                    return ResponseEntity.status(200).body("Success");
                }
            }
        }
        return ResponseEntity.status(404).body("Cart Not Found");
    }

    public ResponseEntity<String> decrementProductId(String token,int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users user = cartControllerForUsers.findByEmail(token,email);
        List<Cart> cartList = cartRepo.findByUserId(user.getId());
        for(Cart c : cartList) {
            if(c.getProductId() == productId) {
                if(c.getQuantity() <= 1)
                    cartRepo.deleteByProductId(c.getProductId(),user.getId());
                else {
                    c.setQuantity(c.getQuantity()-1);
                    cartRepo.save(c);
                }
                return ResponseEntity.status(200).body("Success");
            }
        }
        return ResponseEntity.status(404).body("Fail");
    }

    public ResponseEntity<String> deleteProduct(String token,int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();
        Users user = cartControllerForUsers.findByEmail(token,username);
        List<Cart> cartList = cartRepo.findByUserId(user.getId());
        for(Cart c : cartList) {
            if(c.getProductId() == productId) {
                cartRepo.deleteByProductId(productId,user.getId());
                return ResponseEntity.status(200).body("Success");
            }
        }
        return ResponseEntity.status(404).body("Fail");
    }

    public ResponseEntity<String> deleteCart(int userId) {
        cartRepo.deleteCartByUserId(userId);
        return ResponseEntity.status(200).body("Success");
    }
}

