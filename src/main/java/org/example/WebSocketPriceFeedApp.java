package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.Timer;
import java.util.TimerTask;

public class WebSocketPriceFeedApp {

    public static void main(String[] args) {
        String wsUrl = "wss://your-websocket-feed-url"; // Replace with DEX's WebSocket URL
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(wsUrl).build();
        PriceWebSocketListener listener = new PriceWebSocketListener(new CandlestickManager());
        WebSocket ws = client.newWebSocket(request, listener);

        // Trigger shutdown after 10 minutes for demo purposes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ws.close(1000, "Closing connection");
                client.dispatcher().executorService().shutdown();
            }
        }, 600000);
    }
}

