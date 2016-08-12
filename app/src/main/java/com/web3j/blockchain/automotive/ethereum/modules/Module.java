package com.web3j.blockchain.automotive.ethereum.modules;

import com.web3j.blockchain.automotive.ethereum.network.Request;
import com.web3j.blockchain.automotive.ethereum.network.RequestManager;

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

    public interface ModuleCallback<E> {
        void onSuccess(E res);
        void onFail(String errorMessage);
    }
}
