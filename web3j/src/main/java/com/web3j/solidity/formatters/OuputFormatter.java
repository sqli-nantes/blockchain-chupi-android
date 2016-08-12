package com.web3j.solidity.formatters;

import com.web3j.solidity.SolidityParam;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class OuputFormatter<E> {

    public abstract E format(SolidityParam value);

}
