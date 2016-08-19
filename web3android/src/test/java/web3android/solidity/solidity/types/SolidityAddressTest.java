package web3android.solidity.solidity.types;


import org.junit.Test;

import web3android.solidity.types.SolidityAddress;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityAddressTest {


    String tests[] = new String[]{
        "address",
        "address[]",
        "address[4]",
        "address[][]",
        "address[3][]",
        "address[][6][]"
    };

    SolidityAddress a = new SolidityAddress();

    @Test
    public void testIsType() throws Exception{
        for(String test : tests){
            assertTrue(a.isType(test));
        }
    }


}
