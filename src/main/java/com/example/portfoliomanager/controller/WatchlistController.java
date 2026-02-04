package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.WatchlistItem;
import com.example.portfoliomanager.service.WatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final WatchlistService service;

    public WatchlistController(WatchlistService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<WatchlistItem>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<WatchlistItem> add(@RequestBody WatchlistItem item) {
        WatchlistItem saved = service.add(item);
        return ResponseEntity.created(URI.create("/api/watchlist/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-instrument/{instrumentId}")
    public ResponseEntity<Void> deleteByInstrument(@PathVariable Long instrumentId) {
        service.deleteByInstrumentId(instrumentId);
        return ResponseEntity.noContent().build();
    }
}

