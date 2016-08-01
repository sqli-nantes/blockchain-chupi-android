package com.sqli.blockchain.automotive.ethereum.modules;

import android.util.Log;

import com.sqli.blockchain.automotive.EthereumService;
import com.sqli.blockchain.automotive.ethereum.AsyncRequest;
import com.sqli.blockchain.automotive.ethereum.ErrorResponse;
import com.sqli.blockchain.automotive.ethereum.Formatter;
import com.sqli.blockchain.automotive.ethereum.RequestManager;
import com.sqli.blockchain.automotive.ethereum.SuccessfulResponse;

import java.io.IOException;

/**
 * Created by gunicolas on 27/07/16.
 */
public class Admin extends Module {


    public Admin(RequestManager requestManager) {
        super(requestManager);
    }

    public void addPeer(String _peer) throws IOException {

        String peer = Formatter.formatString(_peer);

        AsyncRequest request = new AsyncRequest("admin_addPeer", peer) {
            @Override
            public void onSuccess(SuccessfulResponse res) {
                Log.d(EthereumService.TAG,res.toString());
            }

            @Override
            public void onFail(ErrorResponse error) {
                Log.d(EthereumService.TAG,error.toString());
            }
        };
        sendRequest(request);
    }

    public void peers() throws IOException {
        AsyncRequest request = new AsyncRequest("admin_peers") {
            @Override
            public void onSuccess(SuccessfulResponse res) {
                Log.d(EthereumService.TAG,res.toString());
            }

            @Override
            public void onFail(ErrorResponse error) {
                Log.d(EthereumService.TAG,error.toString());
            }
        };
        sendRequest(request);
    }

    public void nodeInfo() throws IOException {
        AsyncRequest request = new AsyncRequest("admin_nodeInfo") {
            @Override
            public void onSuccess(SuccessfulResponse res) {
                Log.d(EthereumService.TAG,res.toString());
            }

            @Override
            public void onFail(ErrorResponse error) {
                Log.d(EthereumService.TAG,error.toString());
            }
        };
        sendRequest(request);
    }

}
