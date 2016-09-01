package web3j;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by gunicolas on 23/08/16.
 */
public class UtilsTest {


    @Before
    public void setup() throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();
    }

    @Test
    public void test() throws Exception{


        System.out.println("hi");
    }

}
