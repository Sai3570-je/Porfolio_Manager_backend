package com.example.portfoliomanager.repository;

import com.example.portfoliomanager.beans.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
