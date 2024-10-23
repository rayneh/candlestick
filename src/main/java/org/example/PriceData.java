package org.example;

import java.time.Instant;

public class PriceData {
    private final double price;
    private final Instant timestamp;

    private PriceData(double price, Instant timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    public double getPrice() {
        return price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static PriceData fromReport(String report) {
        // Parse the report to extract price and timestamp
        // This is just an example; you'll need to implement the actual parsing logic
        double price = 0; // Extracted price
        Instant timestamp = Instant.now(); // Example timestamp

        return new PriceData(price, timestamp);
    }
}
