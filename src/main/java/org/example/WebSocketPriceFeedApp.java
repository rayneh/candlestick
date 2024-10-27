package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.CountDownLatch;

public class WebSocketPriceFeedApp {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void start(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WebSocketPriceFeedApp [FeedID]");
            return;
        }

        String feedID = args[0];
        String wsUrl = "wss://ws.testnet-dataengine.chain.link/api/v1/ws?feedIDs=" + feedID;

        HMac hmac = new HMac(args[1], args[2]);
        // Retrieve authorization headers
        String[] headers = hmac.generateHmacAuthorizationHeaders("/api/v1/ws?feedIDs=" + feedID);
        String authorizationHeader = headers[0];
        String timestampHeader = headers[1];
        String signatureHeader = headers[2];

        System.out.println("[INFO] Attempting to connect to WebSocket at: " + wsUrl);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(wsUrl)
                .header("Authorization", authorizationHeader)
                .header("X-Authorization-Timestamp", timestampHeader)
                .header("X-Authorization-Signature-SHA256", signatureHeader)
                .header("Connection", "Upgrade")
                .header("Upgrade", "websocket")
                .build();

        client.newWebSocket(request, new PriceWebSocketListener(latch));
        client.dispatcher().executorService().shutdown();

        // Wait for WebSocket to finish before ending the program
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("[ERROR] WebSocket connection interrupted: " + e.getMessage());
        }
    }
}
