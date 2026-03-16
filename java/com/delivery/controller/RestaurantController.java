package com.delivery.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.delivery.entity.Restaurant;
//import com.delivery.entity.User;
import com.delivery.service.RestaurantService;
//import com.delivery.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }
    
    
    
//    @GetMapping("/by-user/{userId}")
//    public List<Restaurant> getRestaurantsByUserLocation(@PathVariable Long userId){
//
//        User user = UserService.getUser(userId);
//
//        return restaurantService.getRestaurantsByLocation(user.getCity());
//    }

}
