package com.qa.gemini.utils;

import com.gemini.generic.utils.CommonUtils;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataFileReader {

    private static DataFileReader reader;
    private static JsonObject object;

    private DataFileReader() {
        object = loadData(ProjectConfigData.getProperty("jewel_data"));
    }

    public static DataFileReader getInstance() {
        if (reader == null)
            reader = new DataFileReader();
        return reader;
    }

    public static JsonObject loadData(String filePath) {
        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CommonUtils.convertStringInToJsonElement(data).getAsJsonObject();
    }

    public String getDataValue(String key) {
        return CommonUtils.convertJsonElementToString(object.get(key));
    }

    public String getUsername(String userType) {
        return CommonUtils.convertJsonElementToString(object.getAsJsonObject(userType).get("username"));
    }

    public String getPassword(String userType) {
        return CommonUtils.convertJsonElementToString(object.getAsJsonObject(userType).get("password"));
    }

}
