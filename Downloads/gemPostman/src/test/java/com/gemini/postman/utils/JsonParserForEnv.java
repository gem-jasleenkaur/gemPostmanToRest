package com.gemini.postman.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JsonParserForEnv {
    public static String filePath = "C:\\Users\\jasleen.multani\\Downloads\\jewel_UI\\Postman\\src\\test\\resources\\Test Environment.postman_environment (1).json";

    static LinkedHashMap<Object, Object> environment = new LinkedHashMap<>();
    public static void readJson()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        // Read the JSON data from the file
        StringBuilder jsonStringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonStringBuilder.append(line);
        }

        // Parse the JSON data
        String jsonString = jsonStringBuilder.toString();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

        String name = jsonObject.getAsJsonObject().get("name").getAsString();
        String id = jsonObject.getAsJsonObject().get("id").getAsString();
        String variableScope = jsonObject.getAsJsonObject().get("_postman_variable_scope").getAsString();
        String postmanExportedAt = jsonObject.getAsJsonObject().get("_postman_exported_at").getAsString();
        String postmanExportedUsing = jsonObject.getAsJsonObject().get("_postman_exported_using").getAsString();


        // Print the values
        System.out.println("id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Variable Scope: " + variableScope);
        System.out.println("Postman Exported At: " + postmanExportedAt);
        System.out.println("Postman Exported Using: " + postmanExportedUsing);

        JsonArray valuesArray = jsonObject.getAsJsonArray("values");
        System.out.println("Number of environment values are: " +valuesArray.size());
        for(int iteration=0;iteration<valuesArray.size();iteration++)
        {
       //     LinkedHashMap<Object,Object> request = new LinkedHashMap<>();
            System.out.println(valuesArray.get(iteration).getAsJsonObject().get("key"));
            System.out.println(valuesArray.get(iteration).getAsJsonObject().get("value"));
            environment.put(valuesArray.get(iteration).getAsJsonObject().get("key"), valuesArray.get(iteration).getAsJsonObject().get("value"));
            System.out.println(valuesArray.get(iteration).getAsJsonObject().get("type"));
            System.out.println(valuesArray.get(iteration).getAsJsonObject().get("enabled"));
  //          postman.put(requestName,request);
        }

        System.out.println(environment);

        } catch (IOException e) {
        e.printStackTrace();
    }
}
}
