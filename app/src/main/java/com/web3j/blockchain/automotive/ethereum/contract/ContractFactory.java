package com.web3j.blockchain.automotive.ethereum.contract;

/**
 * Created by gunicolas on 3/08/16.
 */
public class ContractFactory {

   /* private JSONArray abi;

    // Key is function name + parameters hash code. Ex: "foo(int a,int b)".hashCode()
    private Map<Integer,SolidityFunction> functions;

    public ContractFactory(JSONArray abi) throws JSONException {
        this.abi = abi;

        this.functions = new HashMap<>();

        extractFunctions();
    }

    private void extractFunctions() throws JSONException {
        for(int i=0;i<abi.length();i++ ){
            JSONObject obj = abi.getJSONObject(i);
            if( obj.get("type").equals("function")){
                String functionId = obj.getString("name");
                functionId += "(" + Utils.stringListToString(Utils.extractParametersTypes(obj.getJSONArray("inputs"))) + ")";
                this.functions.put(functionId.hashCode(),new SolidityFunction(obj));
            }
        }
    }

    public Contract at(String address){
        return new Contract(this.abi,address,null,this.functions);
    }

    public Contract newInstance(){return null;}*/
}
