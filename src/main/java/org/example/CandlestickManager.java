package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CandlestickManager {
    private final List<Candlestick> candlestickList = new ArrayList<>();
    private final List<PriceData> priceCollection = new ArrayList<>();
    private static final long INTERVAL = 60_000; // 1 minute in milliseconds
    private BigDecimal open;
    private BigDecimal high;  // No initial value
    private BigDecimal low;   // No initial value
    private BigDecimal close;
    private long firstTimestamp;
    private long lastTimestamp;

    public void processPriceReport(long timestamp, BigDecimal price) {
        // Decode the report to extract price and timestamp
        PriceData priceData = PriceData.fromReport(timestamp, price);

        // Update the candlestick data based on the extracted information
        updateCandlesticks(priceData);
    }

    private void updateCandlesticks(PriceData priceData) {
        System.out.println("[INFO] Processing price: " + priceData.getPrice() + " at timestamp: " + priceData.getTimestamp());

        long timestamp = priceData.getTimestamp();
        priceCollection.add(priceData);

        if (priceCollection.size() == 1) {
            firstTimestamp = timestamp;
            open = priceCollection.get(0).getPrice();
            high = open; // Initialize high with the first price
            low = open;  // Initialize low with the first price
        }

        if (priceCollection.size() == 60) {
            lastTimestamp = timestamp;
            close = priceCollection.get(59).getPrice();

            for (PriceData e : priceCollection) {
                if (e.getPrice().compareTo(high) > 0) {
                    high = e.getPrice();
                }
                if (e.getPrice().compareTo(low) < 0) {
                    low = e.getPrice();
                }
            }

            // Calculate the start of the current minute interval
            long intervalStart = timestamp - (timestamp % INTERVAL);

            // Create the candlestick for the current interval
            Candlestick candlestick = new Candlestick(open, high, low, close, firstTimestamp, lastTimestamp);
            candlestickList.add(candlestick);

            // Print the candlestick data in the desired format
            System.out.println(candlestick.toOutputFormat());

            priceCollection.clear();
            // Reset high and low for the next interval
            high = null; // Clear reference for next interval
            low = null;  // Clear reference for next interval
        }
    }
}
