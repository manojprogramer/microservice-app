package com.techouts.cartController.dao;

import com.techouts.cartController.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    @Query("From Cart c where c.userId = :userId and c.productId = :productId")
    Cart findByUserAndProductName(@Param("userId") long userId,@Param("productId") int productId);


    @Query("FROM Cart C WHERE C.userId = :userId")
    List<Cart> findByUserId(@Param("userId") Long id);

    @Query(" FROM Cart C WHERE C.userId = :userId and C.productId = :productId")
    List<Cart> findByUserIdAndProductId(@Param("userId") int userId,@Param("productId") long productId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c where c.productId = :productId and c.userId = :userId")
    void deleteByProductId(@Param("productId") int productId, @RequestParam("userId") long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.userId = :userId")
    void deleteCartByUserId(@Param("userId") int userId);
}
