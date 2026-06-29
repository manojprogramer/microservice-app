package com.techouts.logincontroller.controller;



import com.techouts.logincontroller.model.Users;
import com.techouts.logincontroller.service.JwtService;
import com.techouts.logincontroller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @GetMapping("/hi")
    public String greet(){
        return "Welcome to the techouts";
    }

    @GetMapping("/getuser")
    private Users getUser(String email){
        System.out.println("It is calling from the order controller");
        return userService.findByEmail(email);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Users user)
    {
        Map<String,String> response = new HashMap<>();
        if(userService.validateUser(user))
        {

             String token = jwtService.generateToken(user.getEmail());
             response.put("token",token);
             return ResponseEntity.ok(response);
        }
        else {
            response.put("token","Invalid Credentials");
            return ResponseEntity.status(401).body(response);
        }
    }
    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return userService.check();
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user)
    {
        return userService.save(user);
    }
    @GetMapping("/getusers")
    private List<Users> usersList()
    {
        List<Users> users =  userService.findAll();
        System.out.println(users);
        return users;
    }
}
