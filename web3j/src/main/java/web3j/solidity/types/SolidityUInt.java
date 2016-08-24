package web3j.solidity.types;


import java.util.regex.Pattern;

import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityUInt extends SolidityType{

    public SolidityUInt() {
        super(  SolidityFormatters.getInputIntFormatter(),
                SolidityFormatters.getOutputUIntFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^uint(([0-9])*)?(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32 * staticArrayLength(name);
    }
}
