package com.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.delivery.cardentity.AddToCartRequest;
import com.delivery.cardentity.Cart;
import com.delivery.service.CartService;



@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @DeleteMapping("/remove")
    public Cart removeFromCart(@RequestParam Long userId,
                               @RequestParam Long cartItemId) {
        return cartService.removeItem(userId, cartItemId);
    }
    
    

    @GetMapping("/{userId}")
    public Cart viewCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }
    
    
}


