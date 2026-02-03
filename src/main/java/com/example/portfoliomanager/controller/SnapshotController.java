package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.PortfolioSnapshot;
import com.example.portfoliomanager.service.SnapshotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/snapshots")
public class SnapshotController {
    private final SnapshotService service;

    public SnapshotController(SnapshotService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioSnapshot>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioSnapshot> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PortfolioSnapshot> create(@RequestBody PortfolioSnapshot s) {
        PortfolioSnapshot saved = service.save(s);
        return ResponseEntity.created(URI.create("/api/snapshots/" + saved.getId())).body(saved);
    }
}
