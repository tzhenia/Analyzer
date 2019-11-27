package com.tzhenia.analyzer.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Record {
    private String stockSymbol;
    private BigDecimal price;
    private BigDecimal volume;

    public Record(String stockSymbol, BigDecimal price, BigDecimal volume) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.volume = volume;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        Record record = (Record) o;
        return stockSymbol.equals(record.stockSymbol) &&
                price.equals(record.price) &&
                volume.equals(record.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, price, volume);
    }

    @Override
    public String toString() {
        return "Record{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}