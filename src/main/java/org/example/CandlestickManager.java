package org.example;

import java.text.SimpleDateFormat;
import java.util.*;

public class CandlestickManager {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Supported timeframes in milliseconds
    private static final Map<String, Long> TIMEFRAMES = new HashMap<>();

    static {
        TIMEFRAMES.put("1m", 60 * 1000L);
        TIMEFRAMES.put("2m", 2 * 60 * 1000L);
        TIMEFRAMES.put("5m", 5 * 60 * 1000L);
        TIMEFRAMES.put("15m", 15 * 60 * 1000L);
        TIMEFRAMES.put("30m", 30 * 60 * 1000L);
        TIMEFRAMES.put("1h", 60 * 60 * 1000L);
        TIMEFRAMES.put("2h", 2 * 60 * 1000L * 60);
        TIMEFRAMES.put("4h", 4 * 60 * 1000L * 60);
        TIMEFRAMES.put("6h", 6 * 60 * 1000L * 60);
        TIMEFRAMES.put("12h", 12 * 60 * 1000L * 60);
        TIMEFRAMES.put("1d", 24 * 60 * 1000L * 60);
        TIMEFRAMES.put("1w", 7 * 24 * 60 * 1000L * 60);
    }

    // Store OHLC data for different timeframes
    private final Map<String, NavigableMap<Long, Candlestick>> candlestickData = new HashMap<>();

    public void processPriceUpdate(double price, long timestamp) {
        String formattedTimestamp = dateFormat.format(new Date(timestamp)); // Format the timestamp

        for (Map.Entry<String, Long> entry : TIMEFRAMES.entrySet()) {
            String timeframe = entry.getKey();
            long timeframeMillis = entry.getValue();

            long periodStart = (timestamp / timeframeMillis) * timeframeMillis;
            candlestickData.computeIfAbsent(timeframe, k -> new TreeMap<>())
                    .compute(periodStart, (start, candle) -> {
                        if (candle == null) {
                            return new Candlestick(price, price, price, price, timestamp);
                        } else {
                            candle.update(price, timestamp);
                            return candle;
                        }
                    });

            System.out.println("Updated candlestick for " + timeframe + " timeframe at " + formattedTimestamp + ": " +
                    candlestickData.get(timeframe).get(periodStart));
        }
    }
}
