package web3j.solidity.solidity.types;

import org.junit.Test;

import web3j.solidity.types.SolidityDynamicBytes;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
public class SolidityDynamicBytesTest {

    String testsTrue[] = new String[]{
            "bytes",
            "bytes[]",
            "bytes[4]",
            "bytes[][]",
            "bytes[3][]",
            "bytes[][6][]",
            "bytes[3][]"
    };

    String testsFalse[] = new String[]{
            "bytes32",
            "bytes64[]",
            "bytes8[4]",
            "bytes256[][]",
            "bytes64[][6][]"
    };

    SolidityDynamicBytes a = new SolidityDynamicBytes();

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
