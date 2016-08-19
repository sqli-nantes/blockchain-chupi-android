package web3android.solidity.formatters;


import web3android.solidity.SolidityParam;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class OuputFormatter<E> {

    public abstract E format(SolidityParam value);

}
