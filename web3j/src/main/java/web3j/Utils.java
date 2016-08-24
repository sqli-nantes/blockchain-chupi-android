package web3j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Arrays;

import rx.Observable;
import web3j.exception.Web3JException;
import web3j.module.EthereumMethod;

/**
 * Created by gunicolas on 22/08/16.
 */

public abstract class Utils {


    /*
        Remove get keyword in a "get..." method name and remove Upper case of first letter
        Ex: getNodeInfos --> nodeInfos
    */
    public static String formatAsyncMethod(String asyncMethod){
        int index = asyncMethod.indexOf("Async");
        if( index != -1 ){
            asyncMethod = asyncMethod.substring(0,index);
        }
        return asyncMethod;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> extractReturnType(Method m){
        if( m.getReturnType().isAssignableFrom(Observable.class) ) {
            ParameterizedType returnParameterizedType = (ParameterizedType) m.getGenericReturnType();
            return (Class<T>) returnParameterizedType.getActualTypeArguments()[0];
        }
        return (Class<T>) m.getGenericReturnType();
    }

    public static String formatArgsToString(Object[] args) {

        if( args == null ) return "[]";

        StringBuilder stringBuilder = new StringBuilder("[");

        for(Object arg : args){
            stringBuilder.append(formatArgToString(arg));
            stringBuilder.append(",");
        }
        int lastIndexOf = stringBuilder.lastIndexOf(",");
        stringBuilder.replace(lastIndexOf,lastIndexOf+1,"]");

        return stringBuilder.toString();

    }
    public static String formatArgToString(Object arg){
        if( arg instanceof String ){
            return "\""+ arg +"\"";
        } else if( arg instanceof Integer){
            return String.valueOf(arg);
        } else if( arg instanceof BigInteger ){
            return "\"0x"+((BigInteger)arg).toString(16)+"\"";
        }
        return "";
    }

    public static String extractMethodName(Method method) {
        EthereumMethod annotation = method.getAnnotation(EthereumMethod.class);
        if( annotation != null ) return annotation.name();
        else{
            String methodName = method.getName();
            int getIdx = methodName.indexOf("get");
            if( getIdx == -1 ) return methodName;
            else{
                Class module = method.getDeclaringClass();
                methodName = methodName.substring(3,methodName.length()); // Remove get keyword
                char charArray[] = methodName.toCharArray();              //------------------
                charArray[0] = Character.toLowerCase(charArray[0]);       // respect method name format
                methodName = new String(charArray);                       //------------------
                for(Method m : module.getMethods()){
                    if(     m.getName().equals(methodName)
                            && Arrays.equals(m.getParameterTypes(),method.getParameterTypes()) ){
                        return extractMethodName(m);
                    }
                }
            }
        }
        throw new Web3JException("InvocationHandler error: No Ethereum method Found for "+method.getName());
    }
}
