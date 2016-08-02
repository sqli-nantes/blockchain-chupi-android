package com.sqli.blockchain.automotive.ethereum.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gunicolas on 1/08/16.
 */
public class SuccessfulResponse extends Response {

    JSONArray results;

    public SuccessfulResponse(JSONObject res) throws JSONException {
        super(res);
        Object result = res.get("result");
        if( result instanceof  JSONArray ){
            this.results = (JSONArray) result;
        }
        else {
            this.results = new JSONArray();
            this.results.put(result);
        }
    }

    public JSONArray getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "SuccessfulResponse{" +
                super.toString() +
                "results=" + results.toString() +
                '}';
    }
}
