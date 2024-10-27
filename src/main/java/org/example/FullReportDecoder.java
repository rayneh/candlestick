package org.example;

import org.web3j.utils.Numeric;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class FullReportDecoder {
    Data data = new Data();
    BigDecimal decimalValue;

    public BigDecimal fullReportDecoder(String encodedData) {
        String dataHex = Numeric.cleanHexPrefix(encodedData);
        //int offset = 0;
        int offset = dataHex.length() - 450;

        try {
            // FeedID (bytes32) - 32 bytes
            //data.feedID = Numeric.toHexString(TypeDecoder.decode(dataHex.substring(offset, offset += 64), Bytes32.class).getValue());

            // ValidFromTimestamp (uint32) - 4 bytes
            //data.validFromTimestamp = TypeDecoder.decode(dataHex.substring(offset, offset += 8), Uint32.class).getValue();

            // ObservationsTimestamp (uint32) - 4 bytes
            //data.observationsTimestamp = TypeDecoder.decode(dataHex.substring(offset, offset += 8), Uint32.class).getValue();

            // NativeFee (uint192) - 24 bytes
            //data.nativeFee = new BigInteger(dataHex.substring(offset, offset += 48), 16);

            // LinkFee (uint192) - 24 bytes
            //data.linkFee = new BigInteger(dataHex.substring(offset, offset += 48), 16);

            // ExpiresAt (uint32) - 4 bytes
            //data.expiresAt = TypeDecoder.decode(dataHex.substring(offset, offset += 8), Uint32.class).getValue();

            // BenchmarkPrice (uint192) - 24 bytes
            this.data.benchmarkPrice = new BigInteger(dataHex.substring(offset, offset += 48), 16);
            String test = dataHex.substring(offset, offset += 18);

            // Bid (uint192) - 24 bytes
            //data.bid = new BigInteger(dataHex.substring(offset, offset += 48), 16);

            // Ask (uint192) - 24 bytes
            //data.ask = new BigInteger(dataHex.substring(offset, offset += 48), 16);

            // Convert hex to BigInteger
            BigInteger bigIntValue = new BigInteger(test, 16);

            // Convert to decimal with 18 decimals
            this.decimalValue = new BigDecimal(bigIntValue).divide(BigDecimal.TEN.pow(18));
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Data length is insufficient: " + e.getMessage());
        }

        return decimalValue;
    }

}

