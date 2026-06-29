package com.techouts.logincontroller.service;

import com.techouts.logincontroller.dao.UserRepo;
import com.techouts.logincontroller.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<Users> save(Users user) {
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRole("USERS");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return ResponseEntity.ok(userRepo.save(user));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    public List<Users> findAll() {
        return userRepo.findAll();
    }

    public Users findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean validateUser(Users user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            if(authentication.isAuthenticated())
                return true;
        }
        catch (Exception e )
        {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ResponseEntity<String> check() {
        return ResponseEntity.ok("ok");
    }
}