package com.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.delivery.dto.UserDto;
import com.delivery.entity.LoginRequest;
import com.delivery.entity.User;
import com.delivery.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(UserDto userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/loginPage";
    }

    @PostMapping("/login")
    public String login(LoginRequest request,
                        Model model,
                        HttpSession session) {

        try {
            User user = userService.loginUser(
                    request.getEmail(),
                    request.getPassword()
            );

            session.setAttribute("loggedUser", user);

            return "redirect:/restaurants";

        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    
    
    
    
    @PostMapping("/set-address")
    public User setAddress(@RequestParam Long userId,
                           @RequestParam String city,
                           @RequestParam String address){

        return userService.setAddress(userId, city, address);
    } 
    
    
    
    
   
}