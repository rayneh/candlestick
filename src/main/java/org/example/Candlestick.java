package org.example;

import java.math.BigDecimal;

public class Candlestick {
    private final BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private final long openTime;
    private long closeTime;

    public Candlestick(BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, long openTime, long closeTime) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void update(BigDecimal price, long timestamp) {
        this.close = price;
        this.closeTime = timestamp;
        if (price.compareTo(high) > 0) high = price;
        if (price.compareTo(low) < 0) low = price;
    }

    public String toOutputFormat() {
        return String.format("[\n" +
                        "  %d,            // Open time\n" +
                        "  \"%s\",      // Open\n" +
                        "  \"%s\",      // High\n" +
                        "  \"%s\",      // Low\n" +
                        "  \"%s\",      // Close\n" +
                        "  %d,           // Close time\n" +
                        "  \"0\"         // Ignore.\n" +
                        "]",
                openTime,
                open.toPlainString(),
                high.toPlainString(),
                low.toPlainString(),
                close.toPlainString(),
                closeTime
        );
    }
}
