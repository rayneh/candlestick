package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import java.util.Timer;
import java.util.TimerTask;

public class WebSocketPriceFeedApp {

    public void start(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WebSocketPriceFeedApp [FeedID]");
            return;
        }

        String feedID = args[0];
        String wsUrl = "wss://ws.testnet-dataengine.chain.link/api/v1/ws?feedIDs=" + feedID;

        System.out.println("Connecting to WebSocket at: " + wsUrl);

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder().url(wsUrl);

        // Retrieve authorization headers
        String[] headers = HmacUtils.generateHmacAuthorizationHeaders();
        requestBuilder.addHeader("Authorization", headers[0]);
        requestBuilder.addHeader("X-Authorization-Timestamp", headers[1]);
        requestBuilder.addHeader("X-Authorization-Signature-SHA256", headers[2]);

        Request request = requestBuilder.build();

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
