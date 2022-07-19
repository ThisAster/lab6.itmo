package com.freiz.common.util;


import com.freiz.common.data.SpaceMarine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

public final class JsonParser {
    private JsonParser() {
    }
    public static String serialize(HashSet<SpaceMarine> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.ZonedDateTime.class, new ZonedDateTimeSerializer()).create();
        return g.toJson(collectionData);
    }

    public static HashSet<SpaceMarine> deSerialize(String strData) throws JsonSyntaxException, IllegalArgumentException {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.ZonedDateTime.class, new ZonedDateTimeDeserializer()).setPrettyPrinting().create();
        Type type = new TypeToken<HashSet<SpaceMarine>>() {
        }.getType();
        return g.fromJson(strData.trim(), type);
    }
}
