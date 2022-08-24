package com.alisha.server.utilities;

import com.alisha.common.data.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.TreeSet;

public class JsonParser {

    public String serialize(TreeSet<Route> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateSerializer()).create();
        return g.toJson(collectionData);
    }

    public TreeSet<Route> deSerialize(String strData) throws JsonSyntaxException, IllegalArgumentException {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.LocalDate.class, new DateDeserializer()).create();
        Type type = new TypeToken<TreeSet<Route>>() {
        }.getType();
        if ("".equals(strData)) {
            return new TreeSet<>();
        }
        return g.fromJson(strData, type);
    }
}
