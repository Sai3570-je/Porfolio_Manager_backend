package com.example.Portfolio_Manager.service;

import com.example.Portfolio_Manager.beans.Asset;
import com.example.Portfolio_Manager.beans.Portfolio;
import com.example.Portfolio_Manager.repository.AssetRepository;
import com.example.Portfolio_Manager.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private AssetRepository assetRepository;

    // Method to get a portfolio by userId
    @Override
    public Portfolio getPortfolio(Long userId) {
        return portfolioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    // Method to calculate total portfolio value
    @Override
    public Double calculateTotalPortfolioValue(Long portfolioId) {
        List<Asset> assets = assetRepository.findByPortfolioId(portfolioId);
        Double totalValue = 0.0;
        for (Asset asset : assets) {
            totalValue += asset.getCurrentPrice() * asset.getQuantity();
        }
        return totalValue;
    }

    // Add asset to the portfolio
    @Override
    public Asset addAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    // Update an existing asset
    @Override
    public Asset updateAsset(Long id, Asset assetDetails) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
        asset.setSymbol(assetDetails.getSymbol());
        asset.setCompanyName(assetDetails.getCompanyName());
        asset.setQuantity(assetDetails.getQuantity());
        asset.setPurchasePrice(assetDetails.getPurchasePrice());
        asset.setCurrentPrice(assetDetails.getCurrentPrice());
        return assetRepository.save(asset);
    }

    // Delete an asset from the portfolio
    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
        assetRepository.delete(asset);
    }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        // Save the portfolio object to the database
        return portfolioRepository.save(portfolio);
    }
}

