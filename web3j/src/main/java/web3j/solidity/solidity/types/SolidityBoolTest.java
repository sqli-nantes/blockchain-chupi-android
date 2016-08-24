package web3j.solidity.solidity.types;

import org.junit.Test;

import web3j.solidity.types.SolidityBool;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityBoolTest {

    String tests[] = new String[]{
            "bool",
            "bool[]",
            "bool[4]",
            "bool[][]",
            "bool[3][]",
            "bool[][6][]"
    };

    SolidityBool a = new SolidityBool();

    @Test
    public void testIsType() throws Exception{
        for(String test : tests){
            assertTrue(a.isType(test));
        }
    }
}
