package com.delivery.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.entity.Restaurant;
import com.delivery.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

	public Restaurant save(Restaurant restaurant) {
		
		 return restaurantRepository.save(restaurant);
	}
	public List<Restaurant> searchRestaurants(String name, String location) {

	    if (name != null && location != null) {
	        return restaurantRepository
	                .findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location);
	    } 
	    else if (name != null) {
	        return restaurantRepository.findByNameContainingIgnoreCase(name);
	    } 
	    else if (location != null) {
	        return restaurantRepository.findByLocationContainingIgnoreCase(location);
	    } 
	    else {
	        return restaurantRepository.findAll();
	    }
	}
	
	
	
	public List<Restaurant> getRestaurantsByLocation(String location){

        return restaurantRepository
                .findByLocationContainingIgnoreCase(location);
    }
	

}