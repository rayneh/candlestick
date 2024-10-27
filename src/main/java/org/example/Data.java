package org.example;

import java.math.BigInteger;

// Define Data structure to hold decoded values
class Data {
    public String feedID;
    public BigInteger validFromTimestamp;
    public BigInteger observationsTimestamp;
    public BigInteger nativeFee;
    public BigInteger linkFee;
    public BigInteger expiresAt;
    public BigInteger benchmarkPrice;
    public BigInteger bid;
    public BigInteger ask;

    @Override
    public String toString() {
        return "Data{" +
                "feedID='" + feedID + '\'' +
                ", validFromTimestamp=" + validFromTimestamp +
                ", observationsTimestamp=" + observationsTimestamp +
                ", nativeFee=" + nativeFee +
                ", linkFee=" + linkFee +
                ", expiresAt=" + expiresAt +
                ", benchmarkPrice='" + benchmarkPrice + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
