package web3j.solidity.types;


import java.util.regex.Pattern;

import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 5/08/16.
 */
public class SolidityBool extends SolidityType{


    public SolidityBool() {
        super(  SolidityFormatters.getInputBoolFormatter(),
                SolidityFormatters.getOutputBoolFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^bool(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32*staticArrayLength(name);
    }
}
