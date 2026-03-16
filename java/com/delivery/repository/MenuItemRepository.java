package com.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);
    
}
