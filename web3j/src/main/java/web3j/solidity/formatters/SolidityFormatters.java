package web3j.solidity.formatters;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import web3j.solidity.SolidityParam;
import web3j.solidity.Utils;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class SolidityFormatters {

    public static InputFormatter getInputIntFormatter(){

        return new InputFormatter() {
            @Override
            public SolidityParam format(String value) {

                BigDecimal twoCompDec = Utils.toTwosComplement(value);
                MathContext mc = new MathContext(0, RoundingMode.DOWN);
                BigDecimal twoCompDecRounded = twoCompDec.round(mc);
                String twoCompDecRoundedHexa = Utils.bigDecimalToHexString(twoCompDecRounded); //TODO check function done
                String result = Utils.padLeftWithZeros(twoCompDecRoundedHexa,64);
                return new SolidityParam(result);
            }
        };
    }

    public static InputFormatter getInputBytesFormatter(){
        return new InputFormatter() {
            @Override
            public SolidityParam format(String value) {
                String result = Utils.toHex(value).substring(2); //TODO Check function done
                double l = Math.floor((result.length()+63)/64);
                result = Utils.padRightWithZeros(result,(int)l * 64);
                return new SolidityParam(result);
            }
        };
    }

    public static InputFormatter getInputDynamicBytesFormatter(){
        return new InputFormatter() {
            @Override
            public SolidityParam format(String value) {
                String result = Utils.toHex(value).substring(2); //TODO check function done
                int length = result.length() / 2;
                double l = Math.floor((result.length() + 63) / 64);
                result = Utils.padRightWithZeros(result, (int) l * 64);
                return new SolidityParam(getInputIntFormatter().format(String.valueOf(length)).getValue() + result);
            }
        };

    }

    public static InputFormatter getInputStringFormatter(){
        return new InputFormatter(){
            @Override
            public SolidityParam format(String value){
                String result=Utils.utf8ToHex(value).substring(2);
                int length=result.length()/2;
                double l=Math.floor((result.length()+63)/64);
                result=Utils.padRightWithZeros(result,(int)l*64);
                return new SolidityParam(getInputIntFormatter().format(String.valueOf(length)).getValue()+result);
            }
        };
    }

    public static InputFormatter getInputBoolFormatter(){
        return new InputFormatter() {
            @Override
            public SolidityParam format(String value) {
                boolean b = Boolean.valueOf(value);
                String result = "000000000000000000000000000000000000000000000000000000000000000" + (b ? "1" : "0");
                return new SolidityParam(result);
            }
        };
    }

    public static InputFormatter getInputRealFormatter(){
        return new InputFormatter() {
            @Override
            public SolidityParam format(String value) {
                BigDecimal bigD = Utils.toBigDecimal(value);
                BigDecimal bigDReal = bigD.multiply(new BigDecimal(2).pow(128));

                return getInputIntFormatter().format(bigDReal.toPlainString());
            }
        };
    }

    public static OuputFormatter<BigDecimal> getOutputIntFormatter(){
        return new OuputFormatter<BigDecimal>() {
            @Override
            public BigDecimal format(SolidityParam param) {

                String value = param.staticPart();

                if( Utils.isHexNegative(value) ){
                    BigDecimal valueBD = Utils.hexToBigDecimal(value);
                    valueBD = valueBD.subtract(Utils.hexToBigDecimal("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"));
                    return valueBD.subtract(new BigDecimal(1));
                }
                return Utils.hexToBigDecimal(value);
            }
        };
    }

    public static OuputFormatter<BigDecimal> getOutputUIntFormatter(){
        return new OuputFormatter<BigDecimal>() {
            @Override
            public BigDecimal format(SolidityParam param) {
                String value = param.staticPart();
                return Utils.hexToBigDecimal(value);
            }
        };
    }

    public static OuputFormatter<BigDecimal> getOutputRealFormatter(){
        return new OuputFormatter<BigDecimal>() {
            @Override
            public BigDecimal format(SolidityParam param) {
                BigDecimal paramBD = getOutputIntFormatter().format(param);
                return paramBD.divide(new BigDecimal(2).pow(128));
            }
        };
    }

    public static OuputFormatter<BigDecimal> getOutputURealFormatter(){
        return new OuputFormatter<BigDecimal>() {
            @Override
            public BigDecimal format(SolidityParam param) {
                BigDecimal paramBD = getOutputUIntFormatter().format(param);
                return paramBD.divide(new BigDecimal(2).pow(128));
            }
        };
    }

    public static OuputFormatter<Boolean> getOutputBoolFormatter(){
        return new OuputFormatter<Boolean>() {
            @Override
            public Boolean format(SolidityParam param) {
                return param.staticPart().compareTo("0000000000000000000000000000000000000000000000000000000000000001")==0;
            }
        };
    }

    public static OuputFormatter<String> getOuputBytesFormatter(){
        return new OuputFormatter<String>() {
            @Override
            public String format(SolidityParam param) {
                return "0x"+param.staticPart();
            }
        };
    }

    public static OuputFormatter<String> getOutputDynamicBytesFormatter(){
        return new OuputFormatter<String>() {
            @Override
            public String format(SolidityParam param) {
                String paramDynamicPart = param.dynamicPart().substring(0,64);
                BigDecimal paramBD = Utils.hexToBigDecimal(paramDynamicPart);
                int length = paramBD.multiply(new BigDecimal(2)).intValue();
                return "0x"+param.dynamicPart().substring(64,length);

            }
        };
    }

    public static OuputFormatter<String> getOuputStringFormatter(){
        return new OuputFormatter<String>() {
            @Override
            public String format(SolidityParam param) {
                String paramDynamicPart = param.dynamicPart().substring(0,64);
                BigDecimal paramBD = Utils.hexToBigDecimal(paramDynamicPart);
                int length = paramBD.multiply(new BigDecimal(2)).intValue();
                return Utils.hexToUtf8(param.dynamicPart().substring(64,length));
            }
        };
    }

    public static OuputFormatter<String> getOuputAddressFormatter(){
        return new OuputFormatter<String>() {
            @Override
            public String format(SolidityParam param) {
                String value = param.staticPart();
                return "0x"+value.substring(value.length()-40,value.length());
            }
        };
    }


}
