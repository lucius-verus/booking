package com.homework.booking.repository;

import com.homework.booking.model.Order;
import com.homework.booking.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
