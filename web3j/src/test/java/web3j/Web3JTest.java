package web3j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import web3j.module.objects.NodeInfo;
import web3j.net.provider.JavaIpcProvider;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Web3JTest {

    Web3J web3J;
    JavaIpcProvider provider;

    @Before
    public void setup() throws Exception{
        provider = new JavaIpcProvider("/home/gunicolas/Documents/projects/xXx/testBkc/noeud1/geth.ipc");
        provider.listen();
        web3J = new Web3J.Builder()
                .provider(provider)
                .build();
    }

    @Test
    public void test() throws Exception{
        NodeInfo nodeInfo = web3J.admin.nodeInfo();
        System.out.println(nodeInfo.toString());
        provider.stop();
    }

    @After
    public void after() throws Exception{
        provider.stop();
    }


}
