package web3j;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.NoSuchElementException;

import rx.Observable;
import web3j.exception.Web3JException;
import web3j.module.Admin;
import web3j.module.Eth;
import web3j.module.Personal;
import web3j.net.Request;
import web3j.net.provider.Provider;

public class Web3J {

    public Admin admin;
    public Personal personal;
    public Eth eth;

    private Web3J(Admin admin, Personal personal, Eth eth) {
        this.admin = admin;
        this.personal = personal;
        this.eth = eth;
    }

    public static class Builder {

        private InvocationHandler handler;

        public Builder(){}

        public Builder provider(Provider provider) {
            handler = new InvocationHandler(provider);
            return this;
        }

        public Web3J build() {
            if( handler == null ) return null;
            Admin admin = (Admin) Proxy.newProxyInstance(Admin.class.getClassLoader(), new Class[]{Admin.class},handler);
            Personal personal = (Personal) Proxy.newProxyInstance(Personal.class.getClassLoader(),new Class[]{Personal.class},handler);
            Eth eth = (Eth) Proxy.newProxyInstance(Eth.class.getClassLoader(),new Class[]{Eth.class},handler);
            return new Web3J(admin,personal,eth);
        }

    }

    static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        Provider provider;

        public InvocationHandler(Provider _provider) {
            this.provider = _provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String formattedArgs = Utils.formatArgsToString(args);
            String moduleName = method.getDeclaringClass().getSimpleName().toLowerCase();
            String methodName = "_"+Utils.extractMethodName(method);
            Class<?> returnType = Utils.extractReturnType(method);
            Request<?> request = new Request(moduleName+methodName,formattedArgs,returnType);

            Observable<?> result = provider.sendRequest(request);
            return convertToSyncIfNecessary(result, method);
        }

        private <T> Object convertToSyncIfNecessary(Observable<T> requestResult, Method method) throws Web3JException{
            Object result;
            if(method.getReturnType().isAssignableFrom(Observable.class)) {
                result = requestResult;
            } else {
                try {
                    result = requestResult.toBlocking().single();
                }catch(NoSuchElementException e ){
                    throw new Web3JException(e);
                }
            }
            return result;
        }

    }


}
