package com.delivery.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.delivery.entity.SlotBooking;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {
	// List<SlotBooking> findBySlot_IdAndBookingDate(Long slotId, LocalDate bookingDate);

	 List<SlotBooking> findBySlot_IdAndBookingDate(Long slotId, LocalDate date);

	 List<SlotBooking> findByUser_Id(Long userId);
	 
	 
	}

