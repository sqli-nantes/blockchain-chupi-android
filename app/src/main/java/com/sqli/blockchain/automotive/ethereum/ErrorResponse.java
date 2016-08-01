package com.sqli.blockchain.automotive.ethereum;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gunicolas on 1/08/16.
 */
public class ErrorResponse extends Response {

    String message;
    int code;

    public ErrorResponse(JSONObject res) throws JSONException {
        super(res);
        JSONObject errorObj = res.getJSONObject("error");
        this.message = errorObj.getString("message");
        this.code = errorObj.getInt("code");
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                super.toString() +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
