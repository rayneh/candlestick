package org.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class HmacUtils {
    private static final String SHARED_SECRET = "your_shared_secret"; // Replace with your actual shared secret

    public static String generateHmacAuthorizationHeader(String feedID) {
        String userId = UUID.randomUUID().toString(); // Use your actual UUID
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = generateHmacSignature(userId, timestamp);

        return "Authorization: " + userId + "\n" +
                "X-Authorization-Timestamp: " + timestamp + "\n" +
                "X-Authorization-Signature-SHA256: " + signature;
    }

    private static String generateHmacSignature(String userId, String timestamp) {
        try {
            String data = userId + timestamp; // Concatenate user ID and timestamp
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SHARED_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacData); // Encode to Base64
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC signature", e);
        }
    }
}
