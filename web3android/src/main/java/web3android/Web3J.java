package web3android;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import rx.Observable;
import rx.Subscriber;
import web3android.module.Admin;
import web3android.net.provider.Provider;

public class Web3J {

    public Admin admin;

    public Web3J(Admin _admin) {
        admin = _admin;
    }

    public static class Builder {

        private Builder(){}

        public static Web3J build(Provider provider) {

            InvocationHandler handler = new InvocationHandler(provider);
            Admin admin = (Admin) Proxy.newProxyInstance(Admin.class.getClassLoader(), new Class[]{Admin.class},handler);

            return new Web3J(admin);
        }

    }

    static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        Provider provider;

        public InvocationHandler(Provider provider) {
            this.provider = provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String formattedArgs = formatArgs(args);
            Observable result = provider.sendRequest( proxy.getClass().getName().toLowerCase(), method, formattedArgs);
            return convertToSyncIfNecessary(result, method);
        }

        private Object convertToSyncIfNecessary(Observable requestResult, Method method) {
            Object result;
            if(method.getReturnType().isAssignableFrom(Observable.class)) {
                result = requestResult;
            } else {
                result = requestResult.toBlocking().single();
            }
            return result;
        }

        private String formatArgs(Object[] args) {

            if( args.length == 0 ) return "[]";

            StringBuilder stringBuilder = new StringBuilder("[");

            for(Object arg : args){
                stringBuilder.append(formatArg(arg));
                stringBuilder.append(",");
            }
            int lastIndexOf = stringBuilder.lastIndexOf(",");
            stringBuilder.replace(lastIndexOf,lastIndexOf+1,"]");

            return stringBuilder.toString();

        }
        private String formatArg(Object arg){
            if( arg instanceof String ){
                return "\""+ arg +"\"";
            }
            return "";
        }
    }


}
