package com.techouts.orderItemsController.service;

import com.techouts.orderItemsController.dao.OrderItemsRepo;
import com.techouts.orderItemsController.model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemService {

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    public OrderItems save(OrderItems orderItems) {
        System.out.println(orderItems.getProductName());
        return orderItemsRepo.save(orderItems);

    }

    public List<OrderItems> getOrderItems(long id) {
        return orderItemsRepo.getOrderItemsByUserId(id);
    }

    public String cancelOrder(long userId,int productId) {

        orderItemsRepo.cancelOrder( userId,productId);
        return "Success";
    }
}
