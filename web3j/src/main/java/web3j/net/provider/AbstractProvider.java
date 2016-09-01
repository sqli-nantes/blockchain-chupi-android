package web3j.net.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import web3j.exception.Web3JException;
import web3j.gson.BigIntegerTypeAdapter;
import web3j.gson.HashTypeAdapter;
import web3j.gson.ResponseTypeAdapter;
import web3j.module.objects.Hash;
import web3j.net.Request;
import web3j.net.Response;

/**
 * Created by gunicolas on 17/08/16.
 */
public abstract  class AbstractProvider implements Provider{

    protected Map<Integer, Request> requestQueue;

    private int requestNumber = 0;

    Gson gson;

    public AbstractProvider() {
        requestQueue = new HashMap<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(BigInteger.class,new BigIntegerTypeAdapter());
        gsonBuilder.registerTypeHierarchyAdapter(Response.class,new ResponseTypeAdapter(requestQueue));
        gsonBuilder.registerTypeHierarchyAdapter(Hash.class,new HashTypeAdapter());
        gson = gsonBuilder.create();
    }

    protected abstract void sendThroughMedia(String requestString) throws Web3JException;

    @Override
    public Observable sendRequest(final Request request) {

        request.setId(getARequestNumber());

        String stringRequest =  "{" +
                "\"jsonrpc\":\"2.0\"," +
                "\"method\":\""+ request.getMethodCall() +"\"," +
                "\"params\":"+ request.getArguments() +"," +
                "\"id\":"+request.getId()+"" +
                "}";

        sendThroughMedia(stringRequest);

        requestQueue.put(request.getId(),request);

        Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                request.addSubscriber(subscriber);
            }
        });

        return ret;
    }

    public synchronized int getARequestNumber() {
        return requestNumber++;
    }
}
