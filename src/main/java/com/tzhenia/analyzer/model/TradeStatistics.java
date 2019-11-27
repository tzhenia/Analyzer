package com.tzhenia.analyzer.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TradeStatistics {
    private String stockSymbol;
    private BigDecimal openingPrice;
    private BigDecimal closingPrice;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    private BigDecimal averagePrice;
    private BigDecimal volume;

    public TradeStatistics(String stockSymbol, BigDecimal openingPrice, BigDecimal closingPrice, BigDecimal minimumPrice, BigDecimal maximumPrice, BigDecimal averagePrice, BigDecimal volume) {
        this.stockSymbol = stockSymbol;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
        this.averagePrice = averagePrice;
        this.volume = volume;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(BigDecimal minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(BigDecimal maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeStatistics that = (TradeStatistics) o;
        return stockSymbol.equals(that.stockSymbol) &&
                openingPrice.equals(that.openingPrice) &&
                closingPrice.equals(that.closingPrice) &&
                minimumPrice.equals(that.minimumPrice) &&
                maximumPrice.equals(that.maximumPrice) &&
                averagePrice.equals(that.averagePrice) &&
                volume.equals(that.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, openingPrice, closingPrice, minimumPrice, maximumPrice, averagePrice, volume);
    }

    @Override
    public String toString() {
        return "TradeStatistics{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ", minimumPrice=" + minimumPrice +
                ", maximumPrice=" + maximumPrice +
                ", averagePrice=" + averagePrice +
                ", volume=" + volume +
                '}';
    }
}