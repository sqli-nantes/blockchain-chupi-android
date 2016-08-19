package web3android;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import rx.Subscriber;
import web3android.exception.Web3JException;
import web3android.module.objects.Peer;
import web3android.net.provider.IpcProvider;
import web3android.net.provider.Provider;

import static org.junit.Assert.assertEquals;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Web3JTest {

    @Test
    public void main() throws Exception{


        IpcProvider ipcProvider = new IpcProvider("");
        Web3J web3J = Web3J.Builder.build(ipcProvider);

        try {
            List<Peer> peers = web3J.admin.peers();
            System.out.println(peers.toString());

        }catch(Web3JException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void formatArgTest() throws Exception{
        Web3J.InvocationHandler invocationHandler = Web3J.InvocationHandler.class.getDeclaredConstructor(Provider.class).newInstance(new IpcProvider(""));
        Method formatArgMethod = Web3J.InvocationHandler.class.getDeclaredMethod("formatArg", Object.class);

        formatArgMethod.setAccessible(true);

        String formatedArg = (String) formatArgMethod.invoke(invocationHandler,"a");

        assertEquals("\"a\"",formatedArg);
    }


    @Test
    public void formatArgsTest() throws Exception{
        Web3J.InvocationHandler invocationHandler = Web3J.InvocationHandler.class.getDeclaredConstructor(Provider.class).newInstance(new IpcProvider(""));
        Method formatArgsMethod = Web3J.InvocationHandler.class.getDeclaredMethod("formatArgs", Object[].class);
        formatArgsMethod.setAccessible(true);

        Object[] emptyArray = {new String[]{}};
        Object[] fullArray = {new String[]{"aa","bb","cc","dd"}};

        String emptyArrayFormatted = (String) formatArgsMethod.invoke(invocationHandler,emptyArray);
        String fullArrayFormatted = (String) formatArgsMethod.invoke(invocationHandler,fullArray);

        assertEquals("[]",emptyArrayFormatted);
        assertEquals("[\"aa\",\"bb\",\"cc\",\"dd\"]",fullArrayFormatted);
    }

}
