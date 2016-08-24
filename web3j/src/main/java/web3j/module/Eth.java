package web3j.module;

import java.math.BigDecimal;
import java.math.BigInteger;

import rx.Observable;
import web3j.module.objects.Block;
import web3j.module.objects.Transaction;
import web3j.module.objects.TransactionReceipt;

/**
 * Created by gunicolas on 23/08/16.
 */
public interface Eth {

    @EthereumMethod(name="getBalance")
    BigInteger balance(String account);
    Observable<BigDecimal> getBalance(String account);

    @EthereumMethod(name="getBlock")
    Block block(String hash);
    Observable<Block> getBlock(String hash);
    @EthereumMethod(name="getBlock")
    Block block(BigInteger number);
    Observable<Block> getBlock(BigInteger number);

    @EthereumMethod(name="getTransaction")
    Transaction transaction(String hash);
    Observable<Transaction> getTransaction(String hash);

    @EthereumMethod(name="getTransactionReceipt")
    TransactionReceipt transactionReceipt(String hash);
    Observable<TransactionReceipt> getTransactionReceipt(String hash);

}
