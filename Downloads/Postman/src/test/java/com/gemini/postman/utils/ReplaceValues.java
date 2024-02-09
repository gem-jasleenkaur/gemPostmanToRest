package com.gemini.postman.utils;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReplaceValues {

    public static String filePath = "C:\\Users\\jasleen.multani\\Downloads\\jewel_UI\\Postman\\src\\test\\resources\\Demo.postman_collection.json";

    public static void replace() {
        String outputFile = filePath;

        try {
            // Parse JSON from input file
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

            // Replace placeholders
            replacePlaceholders(jsonObject);

            // Write modified JSON back to the same file
            FileWriter fileWriter = new FileWriter(outputFile);
            new Gson().toJson(jsonObject, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            System.out.println("JSON file has been updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replacePlaceholders(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive() && value.getAsString().contains("{{") && value.getAsString().contains("}}")) {
                String replacedValue = value.getAsString().replaceAll("\\{\\{.*?}}", "abc");
                entry.setValue(new JsonPrimitive(replacedValue));
            } else if (value.isJsonObject()) {
                replacePlaceholders(value.getAsJsonObject());
            }
        }
    }
}