package web3j.solidity.types;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import web3j.solidity.SolidityParam;
import web3j.solidity.formatters.InputFormatter;
import web3j.solidity.formatters.OuputFormatter;
import web3j.solidity.formatters.SolidityFormatters;

/**
 * Created by gunicolas on 08/08/16.
 */
public abstract class SolidityType {

    InputFormatter inputFormatter;
    OuputFormatter outputFormatter;

    public SolidityType(InputFormatter inputFormatter, OuputFormatter outputFormatter) {
        this.inputFormatter = inputFormatter;
        this.outputFormatter = outputFormatter;
    }

    public abstract boolean isType(String name);
    public abstract int staticPartLength(String name);

    public List<String> nestedTypes(String name){
        ArrayList<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile("(\\[[0-9]*\\])").matcher(name);
        while(m.find()) matches.add(m.group());
        return matches;
    }

    public boolean isDynamicArray(String name){
        List<String> nestedTypes = this.nestedTypes(name);
        if( nestedTypes == null || nestedTypes.size() == 0 ) return false;
        String last = nestedTypes.get(nestedTypes.size()-1);
        return !Pattern.compile("\\[[0-9]+\\]").matcher(last).matches();
    }

    public boolean isStaticArray(String name){
        List<String> nestedTypes = this.nestedTypes(name);
        if( nestedTypes == null || nestedTypes.size() == 0 ) return false;
        return !isDynamicArray(name);
    }

    public int staticArrayLength(String name){
        List<String> nestedTypes = this.nestedTypes(name);
        if( nestedTypes == null || !isStaticArray(name) ) return 1;
        String last = nestedTypes.get(nestedTypes.size() - 1);
        return Integer.parseInt(last.substring(1,last.length()-1));
    }

    public String nestedName(String name){
        List<String> nestedTypes = nestedTypes(name);
        if( nestedTypes == null || nestedTypes.size() == 0 ) return name;
        String last = nestedTypes.get(nestedTypes.size()-1);
        return name.substring(0,name.length() - last.length());
    }

    public boolean isDynamicType(){
        return false;
    }

    public String encode(Object value,String name){
        if( isDynamicArray(name) ){

            Object[] array = (Object[]) value;
            int length = array.length;
            String nestedName = nestedName(name);

            String result = "";
            InputFormatter inputFormatter = SolidityFormatters.getInputIntFormatter();
            result += inputFormatter.format(String.valueOf(length)).encode();

            for(Object o : array){
               result += encode(o,nestedName);
            }

            return result;


        } else if( isStaticArray(name) ){

            Object[] array = (Object[]) value;

            int length = staticArrayLength(name);
            String nestedName = nestedName(name);

            String result = "";
            for(Object o : array){
                result += encode(o,nestedName);
            }

            return result;
        }
        return inputFormatter.format((String) value).encode(); //TODO possible crash
    }

    public String decode(String bytes,int offset,String name){
        if( isDynamicArray(name) ){
            int arrayOffset = Integer.parseInt("0x"+bytes.substring(offset*2,64),16);
            int length = Integer.parseInt("0x"+bytes.substring(arrayOffset*2,64),16);
            int arrayStart = arrayOffset + 32;

            String nestedName = nestedName(name);
            int nestedStaticPartLength = staticPartLength(name);
            int roundedNestedStaticPartLength = (int) (Math.floor((nestedStaticPartLength+31)/32)*32);

            String result = "";
            for(int i=0;i<length*roundedNestedStaticPartLength;i+=roundedNestedStaticPartLength){
                result += decode(bytes,arrayStart+i,nestedName);
            }

            return result;
        } else if( isStaticArray(name) ){

            int length = staticArrayLength(name);
            int arrayStart = offset;
            String nestedName = nestedName(name);
            int nestedStaticPartLength = staticPartLength(nestedName);
            int roundedNestedStaticPartLength = (int) (Math.floor((nestedStaticPartLength+31)/32)*32);

            String result = "";
            for(int i=0;i<length*roundedNestedStaticPartLength;i+=roundedNestedStaticPartLength){
                result += decode(bytes,arrayStart+i,nestedName);
            }

            return result;
        } else if( isDynamicType() ){
            int dynamicOffset = Integer.parseInt("0x"+bytes.substring(offset*2,64),16);
            int length = Integer.parseInt("0x"+bytes.substring(dynamicOffset*2,64));
            int roundedLength = (int) Math.floor((length+31)/32);
            return (String) outputFormatter.format(new SolidityParam(bytes.substring(dynamicOffset*2,(1+roundedLength)*64),0));
        }

        int length = staticPartLength(name);
        return (String) outputFormatter.format(new SolidityParam(bytes.substring(offset*2,length*2)));
    }

}
