package org.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

public class HMac {
    private static String UUID = "";
    private static String SHARED_SECRET = "";

    public HMac(String uuid, String secret) {
        UUID = uuid;
        SHARED_SECRET = secret;
    }

    public String[] generateHmacAuthorizationHeaders(String path) {
        long timestamp = System.currentTimeMillis();
        String signature = generateHmacSignature(UUID, path, timestamp);

        return new String[]{
                UUID,                // Authorization header
                String.valueOf(timestamp), // X-Authorization-Timestamp
                signature              // X-Authorization-Signature-SHA256
        };
    }

    private static String generateHmacSignature(String userId, String path, long timestamp) {
        try {

            // Create a SHA-256 MessageDigest instance
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");

            // Update the hasher with an empty body (empty byte array)
            hasher.update(new byte[0]); // Empty body

            // Finalize the hash and get the byte array
            byte[] serverBodyHash = hasher.digest();

            // Construct the data to be signed as per API specifications
            String data = generateServerBodyHashString("GET", path, bytesToHex(serverBodyHash), userId, timestamp);
            System.out.println("Data to sign: " + data);

            // Initialize HMAC with SHA-256
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SHARED_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            // Generate HMAC and convert to hex
            byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC signature", e);
        }
    }

    public static String generateServerBodyHashString(String method, String path, String serverBodyHash, String clientId, long timestamp) {
        return String.format("%s %s %s %s %d",
                method,
                path,
                serverBodyHash,
                clientId,
                timestamp
                );
    }

    // Convert byte array to hex string
    private static String bytesToHex(byte[] bytes) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }
}
