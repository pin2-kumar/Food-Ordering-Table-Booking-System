package com.delivery.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.delivery.entity.MenuItem;
import com.delivery.service.MenuItemService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public MenuItem addMenu(@RequestBody MenuItem menuItem) {
        return menuItemService.save(menuItem);
    }

    
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> getMenu(@PathVariable Long restaurantId) {
        return menuItemService.getMenuByRestaurant(restaurantId);
    }
}
