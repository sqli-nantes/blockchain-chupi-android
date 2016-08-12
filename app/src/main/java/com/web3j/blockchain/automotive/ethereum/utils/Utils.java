package com.web3j.blockchain.automotive.ethereum.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gunicolas on 4/08/16.
 */
public abstract class Utils {

    public static String formatString(String s){
        return "\""+s+"\"";
    }

    public static String transformToFullName(JSONObject abi) throws JSONException {
        String name = abi.getString("name");
        if( name.indexOf('(') != -1 ) return name;

        List<String> typeList = extractParametersTypes(abi.getJSONArray("inputs"));

        return name + '(' + stringListToString(typeList) + ')';
    }

    public static String stringListToString(List<String> list){
        if( list.size() == 0 ) return "";
        String typeName = Arrays.toString(list.toArray());
        typeName = typeName.substring(1,typeName.length());
        return typeName = typeName.replaceAll(", ","");
    }

    public static List<String> extractParametersTypes(JSONArray params) throws JSONException {
        List<String> ret = new ArrayList<>();
        for(int i=0;i<params.length();i++){
            ret.add(params.getJSONObject(i).getString("type"));
        }
        return ret;
    }

}
