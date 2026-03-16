package com.delivery.dto;

import java.util.List;

public class OrderDto {

    private Long userId;
    private Long restaurantId;
    private List<OrderItemDto> items;
    
    

    public OrderDto() {}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public List<OrderItemDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDto> items) {
		this.items = items;
	}

    // getters & setters
    
}
