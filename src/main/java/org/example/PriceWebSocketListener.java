package org.example;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;

public class PriceWebSocketListener extends WebSocketListener {
    private final CandlestickManager candlestickManager;

    public PriceWebSocketListener(CandlestickManager candlestickManager) {
        this.candlestickManager = candlestickManager;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("WebSocket connection opened: " + response.message());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Message received: " + text);
        candlestickManager.processPriceReport(text); // Process the incoming message
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("WebSocket failure: " + t.getMessage());
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("WebSocket closed: " + reason);
    }
}
