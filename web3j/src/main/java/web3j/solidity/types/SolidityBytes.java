package web3j.solidity.types;


import java.util.regex.Pattern;

import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityBytes extends SolidityType {

    public SolidityBytes() {
        super(  SolidityFormatters.getInputBytesFormatter(),
                SolidityFormatters.getOuputBytesFormatter());
    }

    @Override
    public boolean isType(String name) {
        return Pattern.compile("^bytes([0-9])+(\\[([0-9])*\\])*$").matcher(name).matches();
    }

    @Override
    public int staticPartLength(String name) {
        if( isType(name) ) {
            int start = "bytes".length();
            int arrayIndex = name.indexOf("[");
            if( arrayIndex == -1 ) arrayIndex = name.length();

            String sizeStr = name.substring(start, arrayIndex);

            return Integer.parseInt(sizeStr) * staticArrayLength(name);

        }
        return -1; //ERROR
    }
}
