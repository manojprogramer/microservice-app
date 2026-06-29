package com.techouts.logincontroller.dao;

import com.techouts.logincontroller.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<Users,Long> {

    @Query("SELECT U FROM Users AS U WHERE U.email = :email")
    Users findByEmail(@Param("email") String email);
}
