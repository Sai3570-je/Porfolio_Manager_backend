package com.example.Portfolio_Manager.service;

import com.example.Portfolio_Manager.beans.Asset;
import com.example.Portfolio_Manager.beans.Portfolio;

public interface PortfolioService {

    Portfolio getPortfolio(Long userId);

    Double calculateTotalPortfolioValue(Long portfolioId);

    Asset addAsset(Asset asset);

    Asset updateAsset(Long id, Asset assetDetails);

    void deleteAsset(Long id);

    Portfolio createPortfolio(Portfolio portfolio);
}

