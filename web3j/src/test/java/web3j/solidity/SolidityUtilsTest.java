package web3j.solidity;


import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;

import web3j.sha3.Sha3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 11/08/16.
 */
public class SolidityUtilsTest {

    @Test
    public void testToBigDecimal() throws Exception{
        BigDecimal bd = Utils.toBigDecimal("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        bd = bd.scaleByPowerOfTen(-(bd.precision()-1));
        assertEquals("1.157920892373162e+77",bd.toPlainString());
    }

    @Test
    public void testHexToBigDecimal() throws Exception{
        assertEquals("-35984.32684",Utils.hexToBigDecimal("-0x8c90.53abc947064ece9a2c67").toPlainString());
        assertEquals("35984.32684",Utils.hexToBigDecimal("0x8c90.53abc947064ece9a2c67").toPlainString());
    }

    @Test
    public void testBigDecimalToHexString() throws Exception{

        String testsEquals[] = new String[]{
            "0x47ac2.cf1224631",
            "0x478a",
            "-0x478a",
            "-0x478a.457b74ca",
            "0x8c90.53abc947064ece9a2c67",
            "-0xfff54.f54fe5f"
        };

        for(String test : testsEquals){
            BigDecimal testBD = Utils.hexToBigDecimal(test);
            assertEquals(test,Utils.bigDecimalToHexString(testBD));
        }
    }

    @Test
    public void testFromWei() throws Exception {
        String actual = Utils.fromWei("-0x65ddf8.354aaa354","ether").toPlainString();
        assertEquals(new BigDecimal("-0.00000000000667596021").toPlainString(),actual);
    }
    @Test
    public void testToWei() throws Exception {
        String actual = Utils.toWei("-0x65ddf8.3554","ether").toPlainString();
        assertEquals(new BigDecimal("-6675960208312988281250000").toPlainString(),actual);
    }

    @Test
    public void testIsStrictAddress() throws Exception {
        assertTrue(Utils.isStrictAddress("0x5a8c31fb4173e1704002217bccd0cb423703a227"));
        assertFalse(Utils.isStrictAddress("0xaae545836"));
    }

    @Test
    public void testIsChecksumAddress() throws Exception{
        assertFalse(Utils.isChecksumAddress("0x1c8aff950685c2ed4bc3174f3472287b56d9517b9c948127319a09a7a36deac8"));
        assertFalse(Utils.isChecksumAddress("0xf19e7d10a295c3e86bc7a78c4286ea8f8066f232"));
        assertTrue(Utils.isChecksumAddress("0xF19e7D10a295C3E86Bc7a78c4286EA8f8066F232"));
    }

    @Test
    public void testSha3() throws Exception {
        String sha3 = Sha3.hash("hello");
        assertEquals("1c8aff950685c2ed4bc3174f3472287b56d9517b9c948127319a09a7a36deac8",sha3);
    }

    @Test
    public void testToChecksumAddress() throws Exception{
        String checksumAddress = Utils.toChecksumAddress("0xf19e7d10a295c3e86bc7a78c4286ea8f8066f232");
        assertEquals("0xF19e7D10a295C3E86Bc7a78c4286EA8f8066F232",checksumAddress);
    }

    @Test
    public void testToHexBoolean() throws Exception{
        assertEquals("0x1",Utils.toHex(true));
        assertEquals("0x0",Utils.toHex(false));
        assertEquals("0x1",Utils.toHex(Boolean.valueOf(true)));
        assertEquals("0x0",Utils.toHex(Boolean.valueOf(false)));
    }

    @Test
    public void testToHexJSON() throws Exception{
        JSONObject json = new JSONObject("{a:\"b\",c:\"d\"}");
        assertEquals("0x7b2261223a2262222c2263223a2264227d",Utils.toHex(json));
    }

    @Test
    public void testToHexAsciiString() throws Exception{
        assertEquals("0x68656c6c6f20776f726c64",Utils.toHex("hello world"));
    }

     @Test
    public void testIsStringNumber() throws Exception{
         assertTrue(Utils.isStringNumber("+35135468"));
         assertTrue(Utils.isStringNumber("-35135468"));
         assertTrue(Utils.isStringNumber("35135468"));

         assertTrue(Utils.isStringNumber("+35135468.56468"));
         assertTrue(Utils.isStringNumber("-35135468.654"));
         assertTrue(Utils.isStringNumber("35135468.63546"));

         assertTrue(Utils.isStringNumber("+.56468"));
         assertTrue(Utils.isStringNumber("+56468."));
         assertTrue(Utils.isStringNumber("-35135468."));
         assertTrue(Utils.isStringNumber("-.654"));
     }
}