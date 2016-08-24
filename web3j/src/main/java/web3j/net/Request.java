package web3j.net;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by gunicolas on 23/08/16.
 */
public class Request<T> {

    String methodCall;
    String arguments;
    Class<T> returnType;
    List<Subscriber> subscribers;
    int id;

    public Request(String methodCall, String arguments, Class<T> returnType) {
        this.methodCall = methodCall;
        this.arguments = arguments;
        this.returnType = returnType;
        subscribers = new ArrayList<>();
    }

    public String getMethodCall() {
        return methodCall;
    }

    public String getArguments() {
        return arguments;
    }

    public Class<T> getReturnType() {
        return returnType;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(Subscriber subscriber){
        this.subscribers.add(subscriber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
