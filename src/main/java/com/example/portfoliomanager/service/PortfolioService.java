package com.example.portfoliomanager.service;

import com.example.portfoliomanager.beans.Asset;
import com.example.portfoliomanager.beans.Portfolio;

public interface PortfolioService {

    Portfolio getPortfolio(Long userId);

    Double calculateTotalPortfolioValue(Long portfolioId);

    Asset addAsset(Asset asset);

    Asset updateAsset(Long id, Asset assetDetails);

    void deleteAsset(Long id);

    Portfolio createPortfolio(Portfolio portfolio);
}

