package com.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.entity.MenuItem;
import com.delivery.entity.Restaurant;
import com.delivery.repository.MenuItemRepository;
import com.delivery.repository.RestaurantRepository;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

 
    public MenuItem save(MenuItem menuItem) {

        Long restaurantId = menuItem.getRestaurant().getId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        menuItem.setRestaurant(restaurant);

        return menuItemRepository.save(menuItem);
    }

    
    public List<MenuItem> getMenuByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}
