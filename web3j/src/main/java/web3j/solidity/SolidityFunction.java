package web3j.solidity;

import java.lang.reflect.Method;

import web3j.module.Eth;
import web3j.module.objects.Hash;
import web3j.module.objects.TransactionRequest;

/**
 * Created by gunicolas on 4/08/16.
 */

/**+
 *
 * @param <T> return type
 */
public class SolidityFunction<T> {

    Method method;
    Eth eth;
    String address;
    Object args;

    public SolidityFunction(Method method, Object[] args,Eth eth, String address) {
        this.method = method;
        this.eth = eth;
        this.address = address;
        this.args = args;
    }

    public Hash sendTransaction(TransactionRequest request){
        return eth.sendTransaction(request);
    }

}
