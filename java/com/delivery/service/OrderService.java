package com.delivery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.dto.OrderDto;
import com.delivery.dto.OrderItemDto;
import com.delivery.entity.MenuItem;
import com.delivery.entity.Order;
import com.delivery.entity.OrderItem;
import com.delivery.entity.OrderStatus;
import com.delivery.entity.Restaurant;
import com.delivery.entity.User;

import com.delivery.repository.MenuItemRepository;
import com.delivery.repository.OrderRepository;
import com.delivery.repository.RestaurantRepository;
import com.delivery.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    

    public Order placeOrder(OrderDto orderDto) {

        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(orderDto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderItemDto itemDto : orderDto.getItems()) {

            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(menuItem.getPrice() * itemDto.getQuantity());
            orderItem.setOrder(order);

            total += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    
    
    
    
    public Order placeOrderFromCart(Long userId, Long restaurantId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

	public Order save(Order order){
        return orderRepository.save(order);
    }
	public void cancelOrder(Long orderId) {

	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    order.setStatus(OrderStatus.CANCELLED);

	    orderRepository.save(order);
	}
	public Order getOrderById(Long orderId) {

	    return orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));
	}
	
	public List<Order> getOrdersByUser(Long userId) {
	    return orderRepository.findByUser_IdAndStatusNot(userId, OrderStatus.CANCELLED);
	}
}
