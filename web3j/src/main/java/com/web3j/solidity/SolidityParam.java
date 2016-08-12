package com.web3j.solidity;

/**
 * Created by gunicolas on 08/08/16.
 */
public class SolidityParam {


    String value;
    int offsetLength;

    public SolidityParam(String value) {
        this.value = value;
        this.offsetLength = 0;
    }

    public SolidityParam(String value, int offsetLength) {
        this.value = value;
        this.offsetLength = offsetLength;
    }
}
