package net.gridcraft.core.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class GsonUtil {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create();

    public static String serialize(Object o) {
        return gson.toJson(o);
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Gson getGson() {
        return gson;
    }
}
