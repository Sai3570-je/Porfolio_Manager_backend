package com.example.portfoliomanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionRequest {
    private Long instrumentId;
    private Double quantity;
    private Double avgPurchasePrice;
    private Double currentPrice;
    private OffsetDateTime purchaseDate;
    private String notes;

    public Long getInstrumentId() { return instrumentId; }
    public void setInstrumentId(Long instrumentId) { this.instrumentId = instrumentId; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public Double getAvgPurchasePrice() { return avgPurchasePrice; }
    public void setAvgPurchasePrice(Double avgPurchasePrice) { this.avgPurchasePrice = avgPurchasePrice; }
    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
    public OffsetDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(OffsetDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
