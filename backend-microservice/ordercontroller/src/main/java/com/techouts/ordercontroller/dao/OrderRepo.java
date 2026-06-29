package com.techouts.ordercontroller.dao;


import com.techouts.ordercontroller.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Integer> {
//    @Query("SELECT O FROM Orders O WHERE O.user.email = :email")
//    List<Orders> findByUsername(@Param("email") String email);
}
