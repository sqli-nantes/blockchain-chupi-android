package com.web3j.blockchain.automotive.ethereum.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gunicolas on 1/08/16.
 */
public abstract class Response {

    private int id;

    public Response(JSONObject obj) throws JSONException {
        this.id = obj.getInt("id");
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id +"\n";
    }
}
