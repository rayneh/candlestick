package org.example;

public class PriceData {
    private final int price;
    private final long timestamp;

    private PriceData(long timestamp, int price) {
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static PriceData fromReport(long timestamp, int price) {
        // Parse the report to extract price and timestamp
        // This is just an example; you'll need to implement the actual parsing logic
        //ouble price = 0; // Extracted price
        //Instant timestamp = Instant.now(); // Example timestamp

        return new PriceData(timestamp, price);
    }
}
