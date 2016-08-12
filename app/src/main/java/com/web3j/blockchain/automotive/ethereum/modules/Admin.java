package com.web3j.blockchain.automotive.ethereum.modules;

import com.web3j.blockchain.automotive.ethereum.network.AsyncRequest;
import com.web3j.blockchain.automotive.ethereum.network.ErrorResponse;
import com.web3j.blockchain.automotive.ethereum.network.RequestManager;
import com.web3j.blockchain.automotive.ethereum.network.SuccessfulResponse;
import com.web3j.blockchain.automotive.ethereum.utils.Peer;
import com.web3j.blockchain.automotive.ethereum.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 27/07/16.
 */
public class Admin extends Module {

    public Admin(RequestManager requestManager) {
        super(requestManager);
    }

    public void addPeer(String _peer, final ModuleCallback<Boolean> callback){
        try {
            String peer = Utils.formatString(_peer);
            AsyncRequest request = new AsyncRequest("admin_addPeer", peer) {
                @Override
                public void onSuccess(SuccessfulResponse res) {
                    try {
                        callback.onSuccess((boolean) res.getResults().get(0));
                    } catch (JSONException e) {
                        callback.onFail("JSON parse error");
                    }
                }

                @Override
                public void onFail(ErrorResponse error) {
                    callback.onFail(error.getMessage());
                }
            };
            sendRequest(request);
        } catch (IOException e) { callback.onFail(e.getMessage()); }
    }
    public void peers(final ModuleCallback<List<Peer>> callback ){
        try {
            AsyncRequest request = new AsyncRequest("admin_peers") {
                @Override
                public void onSuccess(SuccessfulResponse res) {
                    JSONArray results = res.getResults();
                    List<Peer> returnedResult = new ArrayList<>();
                    for(int i=0;i<results.length();i++){
                        try{ returnedResult.add(new Peer((JSONObject) results.get(i))); }
                        catch (JSONException e) { callback.onFail("JSON parsing error"); }
                    }
                }

                @Override
                public void onFail(ErrorResponse error) { callback.onFail(error.getMessage()); }
            };
            sendRequest(request);
        } catch (IOException e) { callback.onFail(e.getMessage()); }
    }
    public void nodeInfo(final ModuleCallback<Boolean> callback){
        try {
            AsyncRequest request = new AsyncRequest("admin_nodeInfo") {
                @Override
                public void onSuccess(SuccessfulResponse res) {
                    try {
                        callback.onSuccess((boolean) res.getResults().get(0));
                    } catch (JSONException e) {
                        callback.onFail("JSON parse error");
                    }
                }
                @Override
                public void onFail(ErrorResponse error) { callback.onFail(error.toString()); }
            };
            sendRequest(request);
        } catch (IOException e) { callback.onFail(e.getMessage()); }
    }



}
