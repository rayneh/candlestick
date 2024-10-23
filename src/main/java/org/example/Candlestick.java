package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Candlestick {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private double open, high, low, close;
    private final long openTime;
    private long closeTime;

    public Candlestick(double open, double high, double low, double close, long openTime) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.openTime = openTime;
    }

    public void update(double price, long timestamp) {
        this.close = price;
        this.closeTime = timestamp;
        if (price > high) high = price;
        if (price < low) low = price;
    }

    @Override
    public String toString() {
        return String.format("Open: %.2f, High: %.2f, Low: %.2f, Close: %.2f, OpenTime: %s, CloseTime: %s",
                open, high, low, close, dateFormat.format(new Date(openTime)), dateFormat.format(new Date(closeTime)));
    }
}
