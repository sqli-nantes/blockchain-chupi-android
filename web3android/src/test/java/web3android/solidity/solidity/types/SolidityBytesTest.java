package web3android.solidity.solidity.types;


import org.junit.Test;

import web3android.solidity.types.SolidityBytes;

import static junit.framework.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityBytesTest {

    String testsTrue[] = new String[]{
            "bytes32",
            "bytes64[]",
            "bytes8[4]",
            "bytes256[][]",
            "bytes64[][6][]"
    };

    String testsFalse[] = new String[]{
            "bytes",
            "bytes[]",
            "bytes[4]",
            "bytes[][]",
            "bytes[][6][]"
    };

    SolidityBytes a = new SolidityBytes();

    @Test
    public void testIsType() throws Exception{
        for(String test : testsTrue){
            assertTrue(a.isType(test));
        }

        for(String test : testsFalse){
            assertTrue(!a.isType(test));
        }
    }

}
