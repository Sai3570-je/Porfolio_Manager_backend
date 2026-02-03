package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.beans.Position;
import com.example.portfoliomanager.dto.PositionRequest;
import com.example.portfoliomanager.repository.InstrumentRepository;
import com.example.portfoliomanager.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService service;
    private final InstrumentRepository instrumentRepository;

    @Autowired
    public PositionController(PositionService service, InstrumentRepository instrumentRepository) {
        this.service = service;
        this.instrumentRepository = instrumentRepository;
    }
    @GetMapping("/holdings-count")
    public Long getHoldingsCount() {
        return service.getHoldingsCount();
    }
    @GetMapping("/total-portfolio-value")
    public Double getTotalPortfolioValue() {
        return service.getTotalPortfolioValue();
    }
    @GetMapping("/total-investment")
    public Double getTotalInvestment() {
        return service.getTotalInvestment();
    }
    @GetMapping("/total-gain-loss")
    public Double getTotalGainLoss() {
        return service.getTotalGainLoss();
    }

    @GetMapping
    public ResponseEntity<List<Position>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PositionRequest req) {
        if (req.getInstrumentId() == null) {
            return ResponseEntity.badRequest().body("instrumentId is required");
        }
        Instrument instrument = instrumentRepository.findById(req.getInstrumentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrument not found"));

        Position p = new Position();
        p.setInstrument(instrument);
        p.setQuantity(req.getQuantity());
        p.setAvgPurchasePrice(req.getAvgPurchasePrice());
        p.setCurrentPrice(req.getCurrentPrice());
        p.setPurchaseDate(req.getPurchaseDate());
        p.setNotes(req.getNotes());

        Position saved = service.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
        @PutMapping("/{id}")
        public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PositionRequest req) {
            return service.findById(id).map(existing -> {
                // only change instrument if instrumentId provided
                if (req.getInstrumentId() != null) {
                    Instrument instrument = instrumentRepository.findById(req.getInstrumentId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrument not found"));
                    existing.setInstrument(instrument);
                }
                // update scalar fields only if present in request
                if (req.getQuantity() != null) existing.setQuantity(req.getQuantity());
                if (req.getAvgPurchasePrice() != null) existing.setAvgPurchasePrice(req.getAvgPurchasePrice());
                if (req.getCurrentPrice() != null) existing.setCurrentPrice(req.getCurrentPrice());
                if (req.getPurchaseDate() != null) existing.setPurchaseDate(req.getPurchaseDate());
                if (req.getNotes() != null) existing.setNotes(req.getNotes());

                Position saved = service.save(existing);
                return ResponseEntity.ok(saved);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found"));
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

