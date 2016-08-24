package web3j.module.objects;

import java.math.BigInteger;

/**
 * Created by gunicolas on 23/08/16.
 */
public class Transaction {

    public String hash;
    public BigInteger nonce;
    public String blockHash;
    public BigInteger blockNumber;
    public BigInteger transactionIndex;
    public String from;
    public String to;
    public BigInteger value;
    public BigInteger gasPrice;
    public BigInteger number;
    public String input;

    @Override
    public String toString() {
        return "Transaction{" +
                "hash='" + hash + '\'' +
                ", nonce=" + nonce +
                ", blockHash='" + blockHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", transactionIndex=" + transactionIndex +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value=" + value +
                ", gasPrice=" + gasPrice +
                ", number=" + number +
                ", input='" + input + '\'' +
                '}';
    }
}
