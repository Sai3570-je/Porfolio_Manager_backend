package com.example.portfoliomanager.controller;

import com.example.portfoliomanager.beans.Asset;
import com.example.portfoliomanager.beans.Portfolio;
import com.example.portfoliomanager.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Add a new portfolio
    @PostMapping
    public ResponseEntity<Portfolio> addPortfolio(@RequestBody Portfolio portfolio) {
        Portfolio createdPortfolio = portfolioService.createPortfolio(portfolio);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
    }

    // Get portfolio by userId
    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long userId) {
        Portfolio portfolio = portfolioService.getPortfolio(userId);
        return ResponseEntity.ok(portfolio);
    }

    // Add new asset to portfolio
    @PostMapping("/asset")
    public ResponseEntity<Asset> addAsset(@RequestBody Asset asset) {
        Asset createdAsset = portfolioService.addAsset(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    // Update an existing asset
    @PutMapping("/asset/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        Asset updatedAsset = portfolioService.updateAsset(id, asset);
        return ResponseEntity.ok(updatedAsset);
    }

    // Delete an asset from portfolio
    @DeleteMapping("/asset/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        portfolioService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

    // Get portfolio's total value
    @GetMapping("/total-value/{portfolioId}")
    public ResponseEntity<Double> getPortfolioValue(@PathVariable Long portfolioId) {
        Double totalValue = portfolioService.calculateTotalPortfolioValue(portfolioId);
        return ResponseEntity.ok(totalValue);
    }
}

