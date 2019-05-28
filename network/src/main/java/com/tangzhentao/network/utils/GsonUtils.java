package com.tangzhentao.network.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
@SuppressLint("LongLogTag")
public class GsonUtils {
    private static final String TAG = "com.tangzhengtao.network.utils.GsonUtils";
    private static Gson sGson;

    private GsonUtils() {
        throw new InitException(GsonUtils.class);
    }

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (Gson.class) {
                if (sGson == null) {
                    sGson = createGson();
                }
            }
        }
        return sGson;
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
                .registerTypeAdapter(float.class, new FloatDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .create();
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || !json.startsWith("{")) {
            return null;
        }
        try {
            return getGson().fromJson(json, clazz);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static <T> T toObject(String result, Type type) {
        try {
            return getGson().fromJson(result, type);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static Object toObjectException(String result, Type type) throws JSONException {
        try {
            return getGson().fromJson(result, type);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new org.json.JSONException("");
        }
    }

    public static <T> List<T> toList(String result, @NonNull TypeToken<List<T>> typeToken) {
        try {
            return getGson().fromJson(result, typeToken.getType());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static <T> List<T> toListException(String result, @NonNull TypeToken<List<T>> typeToken) {
        return getGson().fromJson(result, typeToken.getType());
    }

    public static <T> Map<String, T> toMap(String result, @NonNull TypeToken<Map<String, T>> typeToken) {
        try {
            return getGson().fromJson(result, typeToken.getType());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static <T> List<T> toMapException(String result, @NonNull TypeToken<Map<String, T>> typeToken) {
        return getGson().fromJson(result, typeToken.getType());
    }

    public static String toString(Object object) {
        try {
            return getGson().toJson(object);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
        }
        return "";
    }

    private static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    private static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return 0L;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class FloatDefault0Adapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
        @Override
        public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为float类型,如果后台返回""或者null,则返回0.00
                    return 0.00F;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsFloat();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为double类型,如果后台返回""或者null,则返回0.00
                    return 0.00D;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
}
