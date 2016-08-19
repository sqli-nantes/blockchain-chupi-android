package web3android;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import web3android.module.objects.NodeInfo;
import web3android.net.Response;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Various {

    @Test
    public void various() throws Exception{

/*        String lineArray = "{\"jsonrpc\":\"2.0\",\"id\":74,\"result\":[{\"a\":2,\"as\":[{\"a\":4}]},{\"a\":3,\"as\":[{\"a\":6}]}]}";
        Response response = new Gson().fromJson(lineArray, Response.class);

        Type t1 = new TypeToken<List<A>>(){}.getType();
        List<A> aList = new Gson().fromJson(response.result,t1);


        String lineSingle = "{\"jsonrpc\":\"2.0\",\"id\":74,\"result\":{\"a\":2,\"as\":[{\"a\":4}]} }";
        Response response1 = new Gson().fromJson(lineSingle, Response.class);
        A monA = new Gson().fromJson(response1.result,A.class);*/


        Method m = A.class.getMethod("foo");
        ParameterizedType type = (ParameterizedType) m.getGenericReturnType();
        String lineArray = "{\"jsonrpc\":\"2.0\",\"id\":74,\"result\":[{\"a\":2,\"as\":[{\"a\":4}]},{\"a\":3,\"as\":[{\"a\":6}]}]}";
        Response response = new Gson().fromJson(lineArray, Response.class);
        List<A> aList = new Gson().fromJson(response.result,type);


        /*JsonElement el = new Gson().toJsonTree(response.result);

        NodeInfo nodeInfo = new Gson().fromJson(el,NodeInfo.class);*/


    }

    public class A{
        int a;
        List<A> as;

        public List<A> foo(){return as;}
    }

}
