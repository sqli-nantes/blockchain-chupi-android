package com.sqli.blockchain.automotive.ethereum.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gunicolas on 2/08/16.
 */
public class Peer {

    String id;
    String name;
    String localAddress;
    String remoteAddress;

    public Peer(JSONObject peerObject) throws JSONException {
        this.id = (String) peerObject.get("id");
        this.name = (String) peerObject.get("name");
        JSONObject network = (JSONObject) peerObject.get("network");
        this.localAddress = (String) network.get("localAddress");
        this.remoteAddress = (String) network.get("remoteAddress");
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLocalAddress() {
        return localAddress;
    }
    public String getRemoteAddress() {
        return remoteAddress;
    }
}
