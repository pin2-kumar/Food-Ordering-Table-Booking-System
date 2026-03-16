package com.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.entity.RestaurantSlot;

public interface RestaurantSlotRepository extends JpaRepository<RestaurantSlot, Long> {
    List<RestaurantSlot> findByRestaurantId(Long restaurantId);

	List<RestaurantSlot> findByRestaurantIdAndAvailableTrue(Long restaurantId);
}