package com.delivery.repository;

import com.delivery.entity.Order;
import com.delivery.entity.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userId);

    List<Order> findByUser_IdAndStatusNot(Long userId, OrderStatus status);
}
