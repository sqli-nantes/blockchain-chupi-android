package web3j.net;

import com.google.gson.JsonElement;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Response {

    public int id;
    public JsonElement result;
    public Error error;

    public class Error{
        public int code;
        public String message;
    }

    public boolean isError(){
        return error != null;
    }

}
