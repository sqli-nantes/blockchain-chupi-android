package web3j.solidity.types;


import java.util.regex.Pattern;

import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityString extends SolidityType {

    public SolidityString() {
        super(  SolidityFormatters.getInputStringFormatter(),
                SolidityFormatters.getOuputStringFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^string(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32 * staticArrayLength(name);
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}
