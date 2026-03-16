package com.delivery.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.delivery.cardentity.Cart;
import com.delivery.cardentity.CartItem;
import com.delivery.dto.UserDto;
import com.delivery.entity.Order;
import com.delivery.entity.OrderItem;
import com.delivery.entity.OrderStatus;
import com.delivery.entity.Restaurant;
import com.delivery.entity.User;

import com.delivery.service.CartService;
import com.delivery.service.MenuItemService;
import com.delivery.service.OrderService;
import com.delivery.service.OtpService;
import com.delivery.service.RestaurantService;
import com.delivery.service.SlotBookingService;
import com.delivery.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private SlotBookingService slotBookingService;

    @Autowired
    private OrderService orderService;   
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private UserService userService;
    
    

    @GetMapping("/")
    public String home() {
        return "index";
    }


    
    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }


    
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long menuItemId,
                            @RequestParam int qty,
                            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/loginPage";
        }

        cartService.addToCart(user.getId(), menuItemId, qty);

     
        return "redirect:/cart";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }
    
    
    
    

    @GetMapping("/restaurants")
    public String restaurants(HttpSession session, Model model){

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        model.addAttribute("restaurants",
                restaurantService.getAllRestaurants());

        return "restaurants";
    }
    
    
    
    
    

    @GetMapping("/menu/{id}")
    public String menu(@PathVariable Long id, Model model) {
        model.addAttribute("restaurant",
                restaurantService.getRestaurantById(id));
        model.addAttribute("menu",
                menuItemService.getMenuByRestaurant(id));
        return "menu";
    }
    

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/loginPage";
        }

        model.addAttribute("cart",
                cartService.getCartByUser(user.getId()));

        return "cart";
    
    }

    @GetMapping("/slot")
    public String slot(Model model) {
        model.addAttribute("slots",
                slotBookingService.getSlotsByRestaurant(1L));
        return "slot";
    }


    
    @GetMapping("/slot/{restaurantId}")
    public String slot(@PathVariable Long restaurantId, Model model) {

        model.addAttribute("slots",
                slotBookingService.getSlotsByRestaurant(restaurantId));

        model.addAttribute("restaurantId", restaurantId);

        return "slot";
    }
    
    
    
    
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobile, Model model) {

        otpService.generateOtp(mobile);
        model.addAttribute("mobile", mobile);

        return "verify-otp";
    }
    
    
    
    

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp,
                            HttpSession session) {

        boolean valid = otpService.verifyOtp(mobile, otp);

        if(valid){

            User user = userService.findOrCreateByMobile(mobile);

            session.setAttribute("loggedUser", user);

            // address reset every login
            session.removeAttribute("userCity");
            session.removeAttribute("userAddress");

            return "redirect:/address";
        }

        return "redirect:/loginPage";
    }
    
    
    
    
    @PostMapping("/resend-otp")
    public String resendOtp(@RequestParam String mobile, Model model) {

        otpService.generateOtp(mobile);

        model.addAttribute("mobile", mobile);

        return "verify-otp";
    }
    
    
    
    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam Long cartItemId,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/loginPage";
        }

        cartService.removeItem(user.getId(), cartItemId);

        return "redirect:/cart";
    }
    
    
    
    
    @GetMapping("/payment")
    public String payment(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/loginPage";
        }

        Cart cart = cartService.getCartByUser(user.getId());

        if(cart == null || cart.getItems().isEmpty()){
            return "redirect:/cart";
        }

        model.addAttribute("amount", cart.getTotalPrice());

        return "payment";
    }
    
    
    
    
    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model){

    User user = (User) session.getAttribute("loggedUser");

    if(user == null){
    return "redirect:/loginPage";
    }

    List<Order> orders = orderService.getOrdersByUser(user.getId());

    model.addAttribute("orders", orders);

    return "orders";
    }
    
    
    
    
    
    
    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String method, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        Cart cart = cartService.getCartByUser(user.getId());

        // IMPORTANT CHECK
        if(cart == null || cart.getItems().isEmpty()){
            return "redirect:/cart";
        }

        Order order = new Order();

        order.setUser(user);

        // Restaurant from first cart item
        Restaurant restaurant =
                cart.getItems().get(0).getMenuItem().getRestaurant();

        order.setRestaurant(restaurant);

        order.setTotalPrice(cart.getTotalPrice());

        order.setPaymentMethod(method);

        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cart.getItems()){

            OrderItem item = new OrderItem();

            item.setOrder(order);

            item.setMenuItem(cartItem.getMenuItem());

            item.setQuantity(cartItem.getQuantity());

            item.setPrice(cartItem.getPrice());

            orderItems.add(item);
        }

        order.setItems(orderItems);

        orderService.save(order);

        // Clear cart
        cart.getItems().clear();
        cart.setTotalPrice(0);

        cartService.save(cart);

        return "redirect:/orders";
    }    
    
    
     
    
    @PostMapping("/book-slot")
    public String bookSlot(@RequestParam Long slotId,
                           @RequestParam int seats,
                           @RequestParam int vegCount,
                           @RequestParam int nonVegCount,
                           HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        slotBookingService.bookSlot(user.getId(), slotId, seats, vegCount, nonVegCount);

        return "redirect:/booking-success";
    }
    
    @GetMapping("/booking-success")
    public String bookingSuccess() {
        return "booking-success";
    }

    
    
    
    
    @PostMapping("/cancel-booking")
    public String cancelBooking(@RequestParam Long bookingId,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        slotBookingService.cancelBooking(bookingId);

        return "redirect:/bookings";
    }
    
    
    
    
    @PostMapping("/cancel-order")
    public String cancelOrder(@RequestParam Long orderId,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
    
    
    
    
      
    
    @GetMapping("/order-success")
    public String orderSuccess(HttpSession session, Model model){

    User user = (User) session.getAttribute("loggedUser");

    if(user == null){
    return "redirect:/loginPage";
    }

    List<Order> orders = orderService.getOrdersByUser(user.getId());

    model.addAttribute("orders", orders);

    return "order-success";
    }
    
    
    
    
    
    
    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        model.addAttribute("bookings",
                slotBookingService.getBookingsByUser(user.getId()));

        return "bookings";
    }
    
    
   
    
    @PostMapping("/set-address")
    public String setAddress(@RequestParam String city,
                             @RequestParam String state,
                             @RequestParam String address,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if(user == null){
            return "redirect:/loginPage";
        }

        user.setCity(city);
        user.setState(state);
        user.setAddress(address);

        userService.saveUser(user);

        session.setAttribute("userCity", city);

        return "redirect:/restaurants";
    }
    
    
    
    
    @GetMapping("/address")
    public String addressPage(){
        return "address";
    }
    
    
}    
    