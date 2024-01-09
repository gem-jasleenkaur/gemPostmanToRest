package com.qa.gemini.utils;

import com.gemini.generic.utils.CommonUtils;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EndpointsReader {

    private static EndpointsReader reader;
    private static JsonObject object;

    private EndpointsReader() {
        object = loadEndpoints(ProjectConfigData.getProperty("endpoints"));
    }

    public static EndpointsReader getInstance() {
        if (reader == null)
            reader = new EndpointsReader();
        return reader;
    }

    public static JsonObject loadEndpoints(String filePath) {
        String endpoints = null;
        try {
            endpoints = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CommonUtils.convertStringInToJsonElement(endpoints).getAsJsonObject();
    }

    public String getEndpointValue(String key) {
        return CommonUtils.convertJsonElementToString(object.get(key));
    }

    public static void main(String[] args) {
        System.out.println(getInstance().getEndpointValue("user"));
    }
}
