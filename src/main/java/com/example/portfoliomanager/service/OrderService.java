package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order place(Order order);
    Order save(Order order);
    void delete(Long id);
    Order execute(Long id);
}

