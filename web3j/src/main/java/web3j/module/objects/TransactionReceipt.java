package web3j.module.objects;

import java.math.BigInteger;

/**
 * Created by gunicolas on 23/08/16.
 */
public class TransactionReceipt {

    public String blockHash;
    public BigInteger blockNumber;
    public String transactionHash;
    public BigInteger transactionIndex;
    public String from;
    public String to;
    public BigInteger cumulativeGasUsed;
    public BigInteger gasUsed;
    public String contractAddress;
    //List<Log> logs; //TODO find Log object


    @Override
    public String toString() {
        return "TransactionReceipt{" +
                "blockHash='" + blockHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", transactionHash='" + transactionHash + '\'' +
                ", transactionIndex=" + transactionIndex +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cumulativeGasUsed=" + cumulativeGasUsed +
                ", gasUsed=" + gasUsed +
                ", contractAddress='" + contractAddress + '\'' +
                '}';
    }
}
