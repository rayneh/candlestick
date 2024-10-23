package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

public class PriceWebSocketListener extends WebSocketListener {

    private final CandlestickManager candlestickManager;

    public PriceWebSocketListener(CandlestickManager candlestickManager) {
        this.candlestickManager = candlestickManager;
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        JsonObject json = JsonParser.parseString(text).getAsJsonObject();
        double price = json.get("price").getAsDouble();
        long timestamp = json.get("timestamp").getAsLong();

        candlestickManager.processPriceUpdate(price, timestamp);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        System.out.println("Received ByteString: " + bytes.hex());
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, okhttp3.Response response) {
        t.printStackTrace();
    }
}

