package web3j.solidity.types;


import java.util.regex.Pattern;

import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 4/08/16.
 */
public class SolidityInt extends SolidityType {


    public SolidityInt() {
        super(  SolidityFormatters.getInputIntFormatter(),
                SolidityFormatters.getOutputIntFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^int(([0-9])*)?(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32 * staticArrayLength(name);
    }
}
