package web3j;

import org.junit.Test;

/**
 * Created by gunicolas on 23/08/16.
 */
public class UtilsTest {


    @Test
    public void formatArgTest() throws Exception{
       /* Web3J.InvocationHandler invocationHandler = Web3J.InvocationHandler.class.getDeclaredConstructor(AbstractProvider.class).newInstance(new AndroidIpcProvider(""));
        Method formatArgMethod = Web3J.InvocationHandler.class.getDeclaredMethod("formatArg", Object.class);

        formatArgMethod.setAccessible(true);

        String formatedArg = (String) formatArgMethod.invoke(invocationHandler,"a");

        assertEquals("\"a\"",formatedArg);*/
        //TODO wrong test method
    }


    @Test
    public void formatArgsTest() throws Exception{
        /*Web3J.InvocationHandler invocationHandler = Web3J.InvocationHandler.class.getDeclaredConstructor(AbstractProvider.class).newInstance(new AndroidIpcProvider(""));
        Method formatArgsMethod = Web3J.InvocationHandler.class.getDeclaredMethod("formatArgs", Object[].class);
        formatArgsMethod.setAccessible(true);

        Object[] emptyArray = {new String[]{}};
        Object[] fullArray = {new String[]{"aa","bb","cc","dd"}};

        String emptyArrayFormatted = (String) formatArgsMethod.invoke(invocationHandler,emptyArray);
        String fullArrayFormatted = (String) formatArgsMethod.invoke(invocationHandler,fullArray);

        assertEquals("[]",emptyArrayFormatted);
        assertEquals("[\"aa\",\"bb\",\"cc\",\"dd\"]",fullArrayFormatted);*/
        //TODO wrong test method
    }

}
