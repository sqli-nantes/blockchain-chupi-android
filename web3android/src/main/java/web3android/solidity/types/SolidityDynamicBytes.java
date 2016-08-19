package web3android.solidity.types;


import java.util.regex.Pattern;

import web3android.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityDynamicBytes extends SolidityType {

    public SolidityDynamicBytes() {
        super(  SolidityFormatters.getInputDynamicBytesFormatter(),
                SolidityFormatters.getOutputDynamicBytesFormatter() );
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^bytes(\\[([0-9])*\\])*$").matcher(name).matches();
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
