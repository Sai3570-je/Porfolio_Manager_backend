package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.MarketQuote;
import com.example.portfoliomanager.service.MarketQuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/market/quotes")
public class MarketController {
    private final MarketQuoteService service;

    public MarketController(MarketQuoteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MarketQuote>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketQuote> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-instrument/{instrumentId}")
    public ResponseEntity<List<MarketQuote>> byInstrument(@PathVariable Long instrumentId) {
        return ResponseEntity.ok(service.findByInstrumentId(instrumentId));
    }

    @PostMapping
    public ResponseEntity<MarketQuote> create(@RequestBody MarketQuote quote) {
        MarketQuote saved = service.save(quote);
        return ResponseEntity.created(URI.create("/api/market/quotes/" + saved.getId())).body(saved);
    }
}
