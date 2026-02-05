package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Order;
import com.example.portfoliomanager.exception.BadRequestException;
import com.example.portfoliomanager.exception.ConflictException;
import com.example.portfoliomanager.exception.NotFoundException;
import com.example.portfoliomanager.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
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
        if (order == null) {
            throw new BadRequestException("Order must not be null");
        }
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPlacedAt(OffsetDateTime.now());
        try {
            return repo.save(order);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict saving order: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid order: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Order save(Order order) {
        if (order == null) {
            throw new BadRequestException("Order must not be null");
        }
        try {
            return repo.save(order);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict saving order: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid order: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Order not found with id: " + id);
        }
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Cannot delete order due to database constraints");
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid id: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Order execute(Long id) {
        Order o = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        o.setStatus(Order.OrderStatus.EXECUTED);
        o.setExecutedAt(OffsetDateTime.now());
        try {
            return repo.save(o);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Conflict executing order: " + ex.getMostSpecificCause().getMessage());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid order data: " + ex.getMessage());
        }
    }
}