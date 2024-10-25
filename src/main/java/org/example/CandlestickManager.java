package org.example;

import java.util.ArrayList;
import java.util.List;

public class CandlestickManager {
    private final List<Candlestick> candlestickList = new ArrayList<>();
    private final List<PriceData> priceCollection = new ArrayList<>();
    private static final long INTERVAL = 60_000; // 1 minute in milliseconds
    int open;
    int high = 2400;    // TODO: init better (take median)
    int low = 2100;
    int close;
    long firstTimestamp;
    long lastTimestamp;

    public void processPriceReport(long timestamp, int price) {
        // Decode the report to extract price and timestamp
        PriceData priceData = PriceData.fromReport(timestamp, price);

        // Update the candlestick data based on the extracted information
        updateCandlesticks(priceData);
    }

    private void updateCandlesticks(PriceData priceData) {
        // Implement logic to update candlesticks based on received price data
        // Store candlestick data for multiple timeframes
        System.out.println("Processing price: " + priceData.getPrice() + " at timestamp: " + priceData.getTimestamp());

        long timestamp = priceData.getTimestamp();

        priceCollection.add(priceData);

        System.out.println(priceCollection.size());

        if (priceCollection.size() == 1) {
            firstTimestamp = priceData.getTimestamp();
            open = priceCollection.get(0).getPrice();
        }
        if (priceCollection.size() == 60) {
            lastTimestamp = priceData.getTimestamp();
            close = priceCollection.get(59).getPrice();

            for (PriceData e : priceCollection) {
                if (e.getPrice() > high) {
                    high = e.getPrice();
                }
                if (e.getPrice() < low) {
                    low = e.getPrice();
                }
            }

            // Calculate the start of the current minute interval
            long intervalStart = timestamp - (timestamp % INTERVAL);

            // Get or create the candlestick for the current interval
            int finalHigh = high;
            int finalLow = low;

            Candlestick candlestick = new Candlestick(open, finalHigh, finalLow, close, firstTimestamp, lastTimestamp);
            candlestickList.add(candlestick);

            // Update the candlestick with the new price and other data
            // candlestick.update(price, timestamp);

            // Print the candlestick data in the desired format
            System.out.println(candlestick.toOutputFormat());

            priceCollection.clear();
        }



    }
}
