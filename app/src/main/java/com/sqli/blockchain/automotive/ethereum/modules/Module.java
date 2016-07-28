package com.sqli.blockchain.automotive.ethereum.modules;

import com.sqli.blockchain.automotive.ethereum.Request;
import com.sqli.blockchain.automotive.ethereum.RequestManager;

import java.io.IOException;

/**
 * Created by gunicolas on 27/07/16.
 */
public abstract class Module {

    protected RequestManager requestManager;

    public Module(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void sendRequest(Request request) throws IOException {
        requestManager.sendAsync(request);
    }
}
