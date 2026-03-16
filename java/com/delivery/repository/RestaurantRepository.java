package com.delivery.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByLocationContainingIgnoreCase(String location);

    List<Restaurant> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location);
   

	boolean existsByName(String name);
	
	
}
