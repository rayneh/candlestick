package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

class PriceWebSocketListener extends WebSocketListener {
    private final CountDownLatch latch;
    private final ObjectMapper mapper = new ObjectMapper();
    private final CandlestickManager candlestickManager = new CandlestickManager();

    public PriceWebSocketListener(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull okhttp3.Response response) {
        System.out.println("[INFO] WebSocket connection opened.");
        //webSocket.send("Your initial message if needed");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println("[DEBUG] Received message: " + text);
        // Process incoming text messages if needed
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        // System.out.println("[DEBUG] Received binary message: " + bytes.hex());
        // Attempt to parse binary message as JSON
        try {
            String jsonString = bytes.utf8();
            JsonNode jsonNode = mapper.readTree(jsonString);
            processParsedData(jsonNode);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to parse binary message as JSON: " + e.getMessage());
        }
    }

    private void processParsedData(JsonNode jsonNode) {
        try {
            // Extract timestamps
            long validFromTimestamp = jsonNode.at("/report/validFromTimestamp").asLong();
            long observationsTimestamp = jsonNode.at("/report/observationsTimestamp").asLong();

            // Get the hex-encoded fullReport value
            String fullReportHex = jsonNode.at("/report/fullReport").asText();

            FullReportDecoder fullReportDecoder = new FullReportDecoder();
            BigDecimal dec = fullReportDecoder.fullReportDecoder(fullReportHex);

            candlestickManager.processPriceReport(validFromTimestamp, dec);

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to decode fullReport: " + e.getMessage());
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, @NotNull String reason) {
        System.out.println("[INFO] WebSocket closing: " + code + " / " + reason);
        webSocket.close(1000, null);
        latch.countDown();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, okhttp3.Response response) {
        System.err.println("[ERROR] WebSocket error: " + t.getMessage());
        latch.countDown();
    }
}
