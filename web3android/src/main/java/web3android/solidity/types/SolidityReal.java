package web3android.solidity.types;


import java.util.regex.Pattern;

import web3android.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityReal extends SolidityType {

    public SolidityReal() {
        super(  SolidityFormatters.getInputRealFormatter(),
                SolidityFormatters.getOutputRealFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^real(([0-9])*)?(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32 * staticArrayLength(name);
    }
}
