package web3j.solidity;

import java.util.List;

/**
 * Created by gunicolas on 4/08/16.
 */
public class SolidityFunction {

    private String name;
    private boolean constant;

    private List<String> inputTypes;
    private List<String> outputTypes;

    private String address;

/*
    public SolidityFunction(JSONObject signature) throws JSONException {

        this.name = SolidityUtils.transformToFullName(signature);
        this.constant = signature.getBoolean("constant");

        *//*this.inputTypes = SolidityUtils.extractParametersTypes(signature.getJSONArray("inputs"));
        this.outputTypes = SolidityUtils.extractParametersTypes(signature.getJSONArray("outputs"));
*//*
    }

    public void setAddress(String _address){
        this.address = _address;
    }

    private JSONObject toPayload() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("to",this.address);
        //ret.put("data","0x"+this.signature());
        // TODO
        return ret;

    }*/

    /*private String signature(){
        return hash(this.name).substring(0,8);
    }*/




    public void call(){

    }

    public void sendTransaction(){

    }
}
