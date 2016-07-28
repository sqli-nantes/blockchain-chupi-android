package com.sqli.blockchain.automotive.ethereum.modules;

import android.util.Log;

import com.sqli.blockchain.automotive.EthereumService;
import com.sqli.blockchain.automotive.ethereum.RequestManager;
import com.sqli.blockchain.automotive.ethereum.AsyncRequest;

import java.io.IOException;

/**
 * Created by gunicolas on 27/07/16.
 */
public class Admin extends Module {


    public Admin(RequestManager requestManager) {
        super(requestManager);
    }

    public void addPeer(String peer) throws IOException {
        AsyncRequest request = new AsyncRequest("admin_addPeer", peer) {
            @Override
            public void onSuccess(String data) {
                Log.d(EthereumService.TAG,data);
            }

            @Override
            public void onFail(String error) {
                Log.d(EthereumService.TAG,error);
            }
        };
        sendRequest(request);
    }

    public void peers() throws IOException {
        AsyncRequest request = new AsyncRequest("admin_peers") {
            @Override
            public void onSuccess(String data) {
                Log.d(EthereumService.TAG,data);
            }

            @Override
            public void onFail(String error) {
                Log.d(EthereumService.TAG,error);
            }
        };
        sendRequest(request);
    }

    public void nodeInfo() throws IOException {
        AsyncRequest request = new AsyncRequest("admin_nodeInfo") {
            @Override
            public void onSuccess(String data) {
                Log.d(EthereumService.TAG,data);
            }

            @Override
            public void onFail(String error) {
                Log.d(EthereumService.TAG,error);
            }
        };
        sendRequest(request);
    }

}
