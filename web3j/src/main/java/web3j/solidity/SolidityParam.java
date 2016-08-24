package web3j.solidity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 08/08/16.
 */
public class SolidityParam {


    String value;
    int offsetLength;

    public SolidityParam(String value) {
        this.value = value;
        this.offsetLength = -1;
    }
    public SolidityParam(String value, int offsetLength) {
        this.value = value;
        this.offsetLength = offsetLength;
    }
    public int dynamicPartLength(){
        return this.dynamicPart().length()/2;
    }
    public String dynamicPart() {
        return this.isDynamic() ? this.value : "";
    }
    private boolean isDynamic() {
        return this.offsetLength != -1;
    }

    public SolidityParam newWithOffset(int offset){
        return new SolidityParam(this.value,offset);
    }
    public SolidityParam combine(SolidityParam param){
        return new SolidityParam(this.value+param.value);
    }
    public String offsetAsBytes(){
        BigDecimal twoComp = Utils.toTwosComplement(String.valueOf(this.offsetLength));
        String twoCompHex = Utils.bigDecimalToHexString(twoComp); //TODO check function done
        return !this.isDynamic() ? "" : Utils.padLeftWithZeros(twoCompHex,64);
    }
    public String staticPart(){
        if( !this.isDynamic() ) return this.value;
        return this.offsetAsBytes();
    }

    public String encode(){
        return this.staticPart() + this.dynamicPart();
    }

    public String encodeList(List<SolidityParam> params){
        int totalOffset = params.size()*32;
        List<SolidityParam> offsetParams = new ArrayList<>();
        for(SolidityParam p : params){
            if( !p.isDynamic()) offsetParams.add(p);
            int offset = totalOffset;
            totalOffset += p.dynamicPartLength();
            offsetParams.add(p.newWithOffset(offset));
        }
        String ret = "";
        for( SolidityParam p : offsetParams ){
            ret += p.staticPart();
        }
        for( SolidityParam p : offsetParams ){
            ret += p.dynamicPart();
        }
        return ret;
    }

    public String getValue() {
        return value;
    }
}
