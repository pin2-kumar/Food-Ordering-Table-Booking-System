package com.delivery.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.delivery.entity.RestaurantSlot;
import com.delivery.entity.SlotBooking;
import com.delivery.service.SlotBookingService;

@RestController
@RequestMapping("/api/slots")
public class SlotBookingController {

    @Autowired
    private SlotBookingService bookingService;

    //  BOOK SLOT
    @PostMapping("/book")
    public SlotBooking bookSlot(@RequestParam Long userId,
                                 @RequestParam Long restaurantId,
                                 @RequestParam Long slotId,
                                 @RequestParam int seats,
                                 @RequestParam int vegCount,
                                 @RequestParam int nonVegCount){

        return bookingService.bookSlot(
                userId,
                restaurantId,
                slotId,
                LocalDate.now(),
                seats
        );
    }

    //  CANCEL BOOKING
    @DeleteMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) {

        bookingService.cancelBooking(bookingId);
        return "Booking cancelled successfully";
    }

    //  REMAINING SEATS
    @GetMapping("/remaining")
    public int remainingSeats(@RequestParam Long slotId) {

        return bookingService.getRemainingSeats(slotId, LocalDate.now());
    }

    //  GET SLOTS BY RESTAURANT
    @GetMapping("/restaurant/{restaurantId}")
    public List<RestaurantSlot> getSlots(@PathVariable Long restaurantId) {

        return bookingService.getSlotsByRestaurant(restaurantId);
    
    }
   
}