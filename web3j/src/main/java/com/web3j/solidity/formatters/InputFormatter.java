package com.web3j.solidity.formatters;

import com.web3j.solidity.SolidityParam;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class InputFormatter<E> {

    public abstract SolidityParam format(E value);
}
