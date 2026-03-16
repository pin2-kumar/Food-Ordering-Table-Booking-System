package com.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.cardentity.Cart;
import com.delivery.cardentity.CartItem;
import com.delivery.entity.MenuItem;

import com.delivery.entity.User;
import com.delivery.repository.CartRepository;
import com.delivery.repository.MenuItemRepository;

import com.delivery.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;
    

    public Cart addToCart(Long userId, Long menuItemId, int qty) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + qty);
            existingItem.setPrice(existingItem.getQuantity() * item.getPrice());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setMenuItem(item);
            cartItem.setQuantity(qty);
            cartItem.setPrice(item.getPrice() * qty);
            cartItem.setCart(cart);         
            cart.getItems().add(cartItem);
        }

        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItem(Long userId, Long cartItemId) {

        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));

        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private void recalculate(Cart cart) {
        cart.setTotalPrice(
                cart.getItems().stream().mapToDouble(CartItem::getPrice).sum()
        );
    }

	
	

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

   
}
