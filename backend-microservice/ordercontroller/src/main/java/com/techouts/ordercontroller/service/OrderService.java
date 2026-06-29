package com.techouts.ordercontroller.service;


import com.techouts.ordercontroller.dao.OrderRepo;
import com.techouts.ordercontroller.feign.OrderToGetCart;
import com.techouts.ordercontroller.feign.OrderToGetOrderItems;
import com.techouts.ordercontroller.feign.OrderToGetUser;
import com.techouts.ordercontroller.model.Cart;
import com.techouts.ordercontroller.model.OrderItems;
import com.techouts.ordercontroller.model.Orders;
import com.techouts.ordercontroller.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderToGetCart orderToGetCart;

    @Autowired
    OrderToGetUser orderToGetUser;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderToGetOrderItems orderToGetOrderItems;

    public Orders placeOrder(String token,Orders orders) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String email = authentication.getName();
        System.out.println(email);
        Users user = orderToGetUser.findByEmail(token,email);
        System.out.println(user);
        Orders order = new Orders();
        order.setUser_id(user.getId());
        order.setAddress(orders.getAddress());
        order.setCity(orders.getCity());
        order.setFullName(orders.getFullName());
        order.setOrderStatus("PLACED");
        order.setPaymentMethod(orders.getPaymentMethod());
        order.setPhoneNumber(orders.getPhoneNumber());
        order.setPincode(orders.getPincode());
        order.setTotalAmount(orders.getTotalAmount());

        System.out.println(order.getFullName());
        orderRepo.save(order);

        List<Cart>cartList = orderToGetCart.getCart(token,user.getId());

        for(Cart cartItem : cartList) {
            OrderItems orderItem = new OrderItems();
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductPrice(cartItem.getProductPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUsername(email);
            orderItem.setUserId(user.getId());

            orderToGetOrderItems.save(token,orderItem);
        }
        orderToGetCart.deleteCart(token,user.getId());
        return order;
    }

    public ResponseEntity<List<OrderItems>> getOrders(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users user = orderToGetUser.findByEmail(token,email);
        return orderToGetOrderItems.getOrders(token,user.getId());
    }

    public ResponseEntity<?> cancelOrder(String token,int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email  = authentication.getName();
        Users user = orderToGetUser.findByEmail(token,email);
        return orderToGetOrderItems.cancelOrder(token,user.getId(),productId);
    }
}
