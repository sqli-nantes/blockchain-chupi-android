package com.sqli.blockchain.automotive.ethereum.network;

/**
 * Created by gunicolas on 27/07/16.
 */
public abstract class Request {

    private String name;
    private String[] parameters;

    public Request(String name) {
        this.name = name;
        this.parameters = new String[]{};
    }

    public Request(String name,String parameter){
        this(name);
        parameters = new String[]{parameter};
    }

    public Request(String name,String[] _parameters) {
        this(name);
        this.parameters = _parameters;
    }

    public String getName() {
        return name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public boolean isAsync(){
        return false;
    }
}
