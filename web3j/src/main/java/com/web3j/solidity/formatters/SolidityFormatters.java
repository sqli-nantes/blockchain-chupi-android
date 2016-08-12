package com.web3j.solidity.formatters;

import com.web3j.solidity.SolidityParam;

import java.math.BigDecimal;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class SolidityFormatters {

    public SolidityParam formatInputInt(BigDecimal value){
/*        return new InputFormatter<BigDecimal>() {
            @Override
            public SolidityParam format(BigDecimal value) {

                BigDecimal twoCompDec = Utils.toTwosComplement(value.toPlainString());
                MathContext mc = new MathContext(0,RoundingMode.DOWN);
                BigDecimal twoCompDecRounded = twoCompDec.round(mc);
                String result = Utils.padLeftWithZeros(twoCompDec.toString(),64);

                return new SolidityParam(result);
            }
        }.format(value);*/
        return null;
    }

    public SolidityParam formatInputBytes(String value){
        /*return new InputFormatter<String>() {
            @Override
            public SolidityParam format(String value) {
                String result = Utils.toHex(value);
                double l = Math.floor((result.length()+63)/64);
                result = Utils.padRightWithZeros(result,(int)l * 64);
                return new SolidityParam(result);
            }
        }.format(value);*/
        return null;
    }

}
