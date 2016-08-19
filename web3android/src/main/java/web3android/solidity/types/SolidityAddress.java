package web3android.solidity.types;


import java.util.regex.Pattern;

import web3android.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 5/08/16.
 */
public class SolidityAddress extends SolidityType {


    public SolidityAddress() {
        super(  SolidityFormatters.getInputIntFormatter(),
                SolidityFormatters.getOuputAddressFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^address(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        return 32 * staticArrayLength(name);
    }
}
