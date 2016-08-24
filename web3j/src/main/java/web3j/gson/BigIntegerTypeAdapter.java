package web3j.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.math.BigInteger;

/**
 * Created by gunicolas on 24/08/16.
 */
public class BigIntegerTypeAdapter implements JsonDeserializer<BigInteger>,JsonSerializer<BigInteger> {

    @Override
    public BigInteger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new BigInteger(json.getAsString(),16);
    }

    @Override
    public JsonElement serialize(BigInteger src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString(16));
    }

}
