package com.web3j.solidity.types;

import java.math.BigInteger;

/**
 * Created by gunicolas on 4/08/16.
 */
public class SolidityInteger {


    // 2^Type = [8 - 256]
    public enum Type {
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT
    };

    private Type t;
    boolean unsigned;
    BigInteger value;

    public SolidityInteger(int _value, Type t,boolean unsigned){

        String big = "115792090000000000000000000000000000000000000000000000000000000000000000000000";
        try{
            BigInteger bi = new BigInteger(big);

            System.out.println(bi.bitLength());
            System.out.println(bi.bitCount());
            System.out.println(bi.intValue());
            System.out.println(bi.longValue());
            System.out.println(bi.shortValue());
            System.out.println(bi.byteValue());
            System.out.println(bi.floatValue());
            System.out.println(bi.doubleValue());
            System.out.println(bi.signum());


        } catch( NumberFormatException  e){
            e.printStackTrace();
        }
    }




}
