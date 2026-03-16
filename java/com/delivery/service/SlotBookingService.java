package com.delivery.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.entity.Restaurant;
import com.delivery.entity.RestaurantSlot;
import com.delivery.entity.SlotBooking;
import com.delivery.entity.User;
import com.delivery.repository.RestaurantRepository;
import com.delivery.repository.RestaurantSlotRepository;
import com.delivery.repository.SlotBookingRepository;
import com.delivery.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SlotBookingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private RestaurantRepository restaurantRepository;
    @Autowired 
    private RestaurantSlotRepository slotRepository;
    @Autowired 
    private SlotBookingRepository bookingRepository;

    
    public SlotBooking bookSlot(Long userId,
                                Long restaurantId,
                                Long slotId,
                                LocalDate date,
                                int seats) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        RestaurantSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        //  Get already booked seats for same slot + date
        List<SlotBooking> bookings =
                bookingRepository.findBySlot_IdAndBookingDate(slotId, date);

        int bookedSeats = bookings.stream()
                .mapToInt(SlotBooking::getSeatsBooked)
                .sum();

        if (bookedSeats + seats > slot.getTotalSeats()) {
            throw new RuntimeException("Seats not available");
        }

        //  Create booking
        SlotBooking booking = new SlotBooking();
        booking.setUser(user);
        booking.setRestaurant(restaurant);
        booking.setSlot(slot);
        booking.setBookingDate(date);
        booking.setSeatsBooked(seats);

        return bookingRepository.save(booking);
    }

    // Show only slots (optional: you can show all or available only)
    public List<RestaurantSlot> getSlotsByRestaurant(Long restaurantId) {
        return slotRepository.findByRestaurantId(restaurantId);
    }
    
    
 //  Get remaining seats for a slot on specific date
    public int getRemainingSeats(Long slotId, LocalDate date) {

        RestaurantSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        List<SlotBooking> bookings =
                bookingRepository.findBySlot_IdAndBookingDate(slotId, date);

        int bookedSeats = bookings.stream()
                .mapToInt(SlotBooking::getSeatsBooked)
                .sum();

        return slot.getTotalSeats() - bookedSeats;
    }
    

 // Cancel Booking
    public void cancelBooking(Long bookingId) {

        SlotBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        RestaurantSlot slot = booking.getSlot();

        // Delete booking
        bookingRepository.delete(booking);

        //  After cancel, slot should be available again
        slot.setAvailableSeats(slot.getAvailableSeats() + booking.getSeatsBooked());

        if(slot.getAvailableSeats() > 0){
            slot.setAvailable(true);
        }

        slotRepository.save(slot);
    }
	
    public SlotBooking bookSlot(Long userId,
            Long slotId,
            int seats,
            int vegCount,
            int nonVegCount) {

					User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found"));
					
					RestaurantSlot slot = slotRepository.findById(slotId)
					.orElseThrow(() -> new RuntimeException("Slot not found"));
					
					Restaurant restaurant = slot.getRestaurant();
					
					double vegPrice = 250;
					double nonVegPrice = 350;
					
					double totalAmount = (vegCount * vegPrice) + (nonVegCount * nonVegPrice);
					
					SlotBooking booking = new SlotBooking();
					booking.setUser(user);
					booking.setRestaurant(restaurant);
					booking.setSlot(slot);
					booking.setSeatsBooked(seats);
					booking.setVegCount(vegCount);
					booking.setNonVegCount(nonVegCount);
					booking.setTotalAmount(totalAmount);
					booking.setBookingDate(LocalDate.now());
					
					// reduce available seats
					slot.setAvailableSeats(slot.getAvailableSeats() - seats);
					
					if (slot.getAvailableSeats() <= 0) {
					slot.setAvailable(false);
					}
					
					slotRepository.save(slot);
					
					return bookingRepository.save(booking);
					}
    
    public List<SlotBooking> getBookingsByUser(Long userId){
        return bookingRepository.findByUser_Id(userId);
    }
    
					    
}
	









