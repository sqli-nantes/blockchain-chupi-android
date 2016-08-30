package web3j;

import org.junit.Test;

import java.math.BigDecimal;

import web3j.solidity.SolidityUtils;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Web3JTest {

    @Test
    public void test() throws Exception{
        BigDecimal amount = SolidityUtils.toWei("2", "ether");
        String amountHex = SolidityUtils.toHex(amount);
    }


}
