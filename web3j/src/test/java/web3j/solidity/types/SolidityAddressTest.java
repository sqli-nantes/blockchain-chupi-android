package web3j.solidity.types;


import org.junit.Test;


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
