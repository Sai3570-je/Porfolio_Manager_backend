package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Order;
import com.example.portfoliomanager.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Order>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> place(@RequestBody Order order) {
        Order placed = service.place(order);
        return ResponseEntity.created(URI.create("/api/orders/" + placed.getId())).body(placed);
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<Order> execute(@PathVariable Long id) {
        return ResponseEntity.ok(service.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        return service.findById(id).map(existing -> {
            order.setId(id);
            Order saved = service.save(order);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
