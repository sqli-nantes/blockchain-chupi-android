package web3j.net.provider;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import web3j.exception.Web3JException;
import web3j.net.Request;

/**
 * Created by gunicolas on 17/08/16.
 */
public abstract  class AbstractProvider implements Provider{

    protected Map<Integer, Request> requestQueue;

    private static int requestNumber = 1;

    public AbstractProvider() {
        requestQueue = new HashMap<>();
    }

    protected abstract void sendThroughMedia(String requestString) throws Web3JException;

    @Override
    public Observable sendRequest(final Request request) {

        request.setId(requestNumber);

        String stringRequest =  "{" +
                "\"jsonrpc\":\"2.0\"," +
                "\"method\":\""+ request.getMethodCall() +"\"," +
                "\"params\":"+ request.getArguments() +"," +
                "\"id\":"+request.getId()+"" +
                "}";

        sendThroughMedia(stringRequest);

        requestQueue.put(request.getId(),request);
        requestNumber++;

        Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                request.addSubscriber(subscriber);
            }
        });

        return ret;
    }
}
