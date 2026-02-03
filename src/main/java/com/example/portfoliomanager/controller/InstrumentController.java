package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Instrument;
import com.example.portfoliomanager.service.InstrumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {
    private final InstrumentService service;

    public InstrumentController(InstrumentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-symbol/{symbol}")
    public ResponseEntity<Instrument> getBySymbol(@PathVariable String symbol) {
        return service.findBySymbol(symbol).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instrument> create(@RequestBody Instrument instrument) {
        Instrument saved = service.save(instrument); // service will throw 409 if duplicate
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrument> update(@PathVariable Long id, @RequestBody Instrument instrument) {
        return service.findById(id).map(existing -> {
            instrument.setId(id);
            Instrument saved = service.save(instrument);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
