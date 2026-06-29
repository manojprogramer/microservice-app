package com.techouts.orderItemsController.dao;

import com.techouts.orderItemsController.model.OrderItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Integer> {

    @Query("FROM OrderItems OI WHERE OI.userId = userId")
    List<OrderItems> getOrderItemsByUserId(@Param("userId") long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderItems OI WHERE OI.userId = :userId AND OI.productId = :productId")
    void cancelOrder(@Param("userId") long userId,@Param("productId") int productId);
}
