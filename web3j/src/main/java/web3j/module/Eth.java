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

    @EthereumMethod(name="getBlockByHash")
    Block blockByHash(String hash,boolean withTxObjects);
    Observable<Block> getBlockByHash(String hash,boolean withTxObjects);

    @EthereumMethod(name="getBlockByNumber")
    Block blockByNumber(BigInteger number,boolean withTxObjects);
    Observable<Block> getBlockByNumber(BigInteger number,boolean withTxObjects);

    @EthereumMethod(name="getTransactionByHash")
    Transaction transactionByHash(String hash);
    Observable<Transaction> getTransactionByHash(String hash);

    @EthereumMethod(name="getTransactionReceipt")
    TransactionReceipt transactionReceipt(String hash);
    Observable<TransactionReceipt> getTransactionReceipt(String hash);



}
