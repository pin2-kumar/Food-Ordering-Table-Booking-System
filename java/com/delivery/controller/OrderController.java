package com.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.delivery.dto.OrderDto;
import com.delivery.entity.Order;

import com.delivery.service.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/order")

public class OrderController {

    @Autowired
    private OrderService orderService;
    
//    @Autowired
//    private CartService cartService;

    @PostMapping("/place")
    @Transactional
    public String placeOrder(OrderDto orderDto) {

        orderService.placeOrder(orderDto);

        return "redirect:/orders/" + orderDto.getUserId();
    }

    
    @PostMapping("/from-cart")
    
    public Order placeOrderFromCart(@RequestParam Long userId,
                                    @RequestParam Long restaurantId) {
        return orderService.placeOrderFromCart(userId, restaurantId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
    
    
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam Long orderId){

        orderService.cancelOrder(orderId);

        return "Order Cancelled";
    }
   
   
}
