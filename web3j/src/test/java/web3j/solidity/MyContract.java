package web3j.solidity;

import rx.Observable;
import web3j.solidity.types.SolidityInt;

/**
 * Created by gunicolas on 30/08/16.
 */
public interface MyContract extends ContractType {

    SolidityFunction<Void> foo(SolidityInt a);
    Observable fooAsync(SolidityInt a);


    SolidityFunction<SolidityInt> i();
    Observable<SolidityInt> getI();

}
