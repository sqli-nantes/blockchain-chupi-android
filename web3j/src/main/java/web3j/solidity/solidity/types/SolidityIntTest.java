package web3j.solidity.solidity.types;


import org.junit.Test;

import web3j.solidity.types.SolidityInt;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityIntTest {

    String testsTrue[] = new String[]{
        "int",
        "int[]",
        "int[4]",
        "int[][]",
        "int[3][]",
        "int[][6][]",
        "int32",
        "int64[]",
        "int8[4]",
        "int256[][]",
        "int[3][]",
        "int64[][6][]"
    };

    SolidityInt a = new SolidityInt();

    @Test
    public void testIsType() throws Exception{
        for(String test : testsTrue){
            assertTrue(a.isType(test));
        }
    }
}
