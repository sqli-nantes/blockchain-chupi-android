package web3android.net.provider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import web3android.exception.Web3JException;

/**
 * Created by gunicolas on 17/08/16.
 */
public abstract class Provider {

    protected Map<Integer, Object[]> requestQueue;

    private static int requestNumber = 1;

    public Provider() {
        requestQueue = new HashMap<>();
    }

    public Observable sendRequest(String className, final Method method, String formattedArgs) {

        String methodName = className + "_" + method.getName();

        String stringRequest =  "{" +
                "\"jsonrpc\":\"2.0\"," +
                "\"method\":\""+ methodName +"\"," +
                "\"params\":"+ formattedArgs +"," +
                "\"id\":"+requestNumber+"" +
                "}";

        sendThroughMedia(stringRequest);

        Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                requestQueue.put(requestNumber,new Object[]{subscriber,method});
            }
        });


        requestNumber++;


        return ret;
    }

    protected abstract void sendThroughMedia(String requestString) throws Web3JException;

}
