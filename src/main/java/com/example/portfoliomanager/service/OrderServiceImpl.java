package com.example.portfoliomanager.service;


import com.example.portfoliomanager.beans.Order;
import com.example.portfoliomanager.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repo;

    @Autowired
    public OrderServiceImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Order> findAll() { return repo.findAll(); }

    @Override
    public Optional<Order> findById(Long id) { return repo.findById(id); }

    @Override
    @Transactional
    public Order place(Order order) {
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPlacedAt(OffsetDateTime.now());
        return repo.save(order);
    }

    @Override
    @Transactional
    public Order save(Order order) { return repo.save(order); }

    @Override
    @Transactional
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    @Transactional
    public Order execute(Long id) {
        Order o = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found: " + id));
        o.setStatus(Order.OrderStatus.EXECUTED);
        o.setExecutedAt(OffsetDateTime.now());
        return repo.save(o);
    }
}