package com.bookpurple.nocca_admin.network.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/*
 * Class to hold gson parser and type adapter factory related logic
 * Written by gauravsharma on 2019-05-19.
 */
public class ResponseParser {

    private final Gson gson;

    private ResponseParser() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.serializeSpecialFloatingPointValues();
        gson = gsonBuilder.create();
    }

    private ResponseParser(List<RuntimeTypeAdapterFactory> runtimeTypeAdapterFactoryList) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        if (runtimeTypeAdapterFactoryList != null && !runtimeTypeAdapterFactoryList.isEmpty()) {
            for (RuntimeTypeAdapterFactory typeAdapterFactory : runtimeTypeAdapterFactoryList) {
                gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory);
            }
        }

        gsonBuilder.serializeSpecialFloatingPointValues();
        gson = gsonBuilder.create();
    }

    public static synchronized ResponseParser getInstance() {
        return new ResponseParser();
    }

    public static synchronized ResponseParser getInstance(List<RuntimeTypeAdapterFactory>
                                                                  runtimeTypeAdapterFactoryList) {
        return new ResponseParser(runtimeTypeAdapterFactoryList);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <T> T fromJson(String json, Type clazz) {
        return gson.fromJson(json, clazz);
    }

    public Gson getResponseParser() {
        return gson;
    }
}
