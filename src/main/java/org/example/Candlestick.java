package org.example;

public class Candlestick {
    private final double open;
    private double high;
    private double low;
    private double close;
    private final long openTime;
    private long closeTime;

    public Candlestick(double open, double high, double low, double close, long openTime, long closeTime) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void update(double price, long timestamp) { //TODO: mb move logic out of manager
        this.close = price;
        this.closeTime = timestamp;
        if (price > high) high = price;
        if (price < low) low = price;
    }

    public String toOutputFormat() {
        return String.format("[\n" +
                        "  %d,            // Open time\n" +
                        "  \"%.2f\",      // Open\n" +
                        "  \"%.2f\",      // High\n" +
                        "  \"%.2f\",      // Low\n" +
                        "  \"%.2f\",      // Close\n" +
                        "  %d,           // Close time\n" +
                        "  \"0\"         // Ignore.\n" +
                        "]",
                openTime,
                open,
                high,
                low,
                close,
                closeTime
        );
    }
}
