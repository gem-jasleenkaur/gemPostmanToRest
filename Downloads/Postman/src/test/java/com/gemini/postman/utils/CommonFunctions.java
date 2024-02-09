package com.gemini.postman.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {

    private static LinkedList<Object> concatenateValues = new LinkedList<>();

    public static void storePostmanJsonToSmallerJson() throws JsonProcessingException {
        String json = loadJsonFromFile("src/test/resources/SPA-dev.postman_collection 1 (3) (1).json");

        // Parse JSON and extract information
        Map<String, Map<String, JSONObject>> apiInfoMap = parseJson(json);

        // Write endpoint information to JSON files
        for (Map.Entry<String, Map<String, JSONObject>> serviceEntry : apiInfoMap.entrySet()) {
            String serviceName = serviceEntry.getKey();
            Map<String, JSONObject> endpointInfoMap = serviceEntry.getValue();

            // Create a directory for each API info
            String apiInfoDirectoryPath = "src/test/resources/apidata/" + serviceName;
            createOutputDirectory(apiInfoDirectoryPath);

            for (Map.Entry<String, JSONObject> endpointEntry : endpointInfoMap.entrySet()) {
                JSONObject endpointInfo = endpointEntry.getValue();

                // Replace variables in the endpointInfo JSON
              //  endpointInfo = replaceVariables(endpointInfo, yourVariablesMap);

                // Write endpointInfo to a file within the API info directory
                String fileName = serviceName + "_" + endpointEntry.getKey() + ".json";
                writeJsonToFile(apiInfoDirectoryPath + "/" + fileName, endpointInfo);
            }
        }
    }

    private static JSONObject replaceVariables(JSONObject input, Map<String, String> variablesMap) {
        // Define your variable replacement logic here
        // For example, use a regular expression to find and replace variables
        Pattern pattern = Pattern.compile("\\{\\{([^{}]+)}}");
        Matcher matcher = pattern.matcher(input.toString());

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String variableKey = matcher.group(1);
            // Assuming your keys directly match the keys in variablesMap
            String variableValue = variablesMap.getOrDefault(variableKey, "");
            matcher.appendReplacement(result, variableValue);
        }
        matcher.appendTail(result);

        return new JSONObject(result.toString());
    }

    private static void createOutputDirectory(String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new RuntimeException("Error creating output directory: " + directoryPath, e);
        }
    }

    private static void writeJsonToFile(String filePath, JSONObject jsonContent) {
        try {
            Files.write(Paths.get(filePath), jsonContent.toString().getBytes());
            System.out.println("File created: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }

    private static String loadJsonFromFile(String filePath) {
        // Load the JSON file using the correct path resolution
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    private static Map<String, Map<String, JSONObject>> parseJson(String jsonString) {

        Map<String, Map<String, String>> apiInfoMap = new HashMap<>();

        Map<String, Map<String, JSONObject>> apiInfoMapJson = new HashMap<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray itemsArray = jsonObject.getJSONArray("item");

        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject serviceObject = itemsArray.getJSONObject(i);
            String serviceName = serviceObject.getString("name");

            Map<String, String> endpointInfoMap = new HashMap<>();
            Map<String, JSONObject> endpointInfoMapJson = new HashMap<>();

            if (serviceObject.has("item")) {
                JSONArray endpointsArray = serviceObject.getJSONArray("item");
                for (int j = 0; j < endpointsArray.length(); j++) {
                    JSONObject endpointObject = endpointsArray.getJSONObject(j);
                    String endpointName = endpointObject.getString("name");
                    String requestMethod = endpointObject.getJSONObject("request").getString("method");
                    String requestUrl = endpointObject.getJSONObject("request").getJSONObject("url").getString("raw");
                    JSONObject endpointInfoJson = new JSONObject();
                    endpointInfoJson.put("method",requestMethod);
                    endpointInfoJson.put("url",requestUrl);

                    String endpointInfo = String.format("method: %s, url: %s", requestMethod, requestUrl);

                    Object requestHeader = "";
                    if (endpointObject.getJSONObject("request").has("header")) {
                        requestHeader = endpointObject.getJSONObject("request").getJSONArray("header");
                        endpointInfo += String.format(", headers: %s", requestHeader);
                        endpointInfoJson.put("headers",requestHeader);
                    }
                    if (endpointObject.getJSONObject("request").has("body")) {
                        // Only add BODY information for POST and PUT methods
                        String requestBody = endpointObject.getJSONObject("request").getJSONObject("body").getString("raw");
                        endpointInfo += String.format(", body: \"%s\"", requestBody);
                        endpointInfoJson.put("body",requestBody);
                    }

                    endpointInfoMap.put(endpointName, endpointInfo);
                    endpointInfoMapJson.put(serviceName, endpointInfoJson);
                }
            } else {
                // Handle the case where there is no "item" under "name"
                // You can add custom logic here based on your requirements
                // For example, you might want to extract information directly from this level
                String requestMethod = serviceObject.getJSONObject("request").getString("method");
                String requestUrl = serviceObject.getJSONObject("request").getJSONObject("url").getString("raw");
                JSONObject endpointInfoJson = new JSONObject();
                endpointInfoJson.put("method",requestMethod);
                endpointInfoJson.put("url",requestUrl);
                String endpointInfo = String.format("Method: %s, URL: %s", requestMethod, requestUrl);

                Object requestHeader = "";
                if (serviceObject.getJSONObject("request").has("header")) {
                    requestHeader = serviceObject.getJSONObject("request").getJSONArray("header");
                    endpointInfo += String.format(", HEADERS: %s", requestHeader);
                    endpointInfoJson.put("headers",requestHeader);
                }
                if (serviceObject.getJSONObject("request").has("body")) {
                    // Only add BODY information for POST and PUT methods
                    String requestBody = serviceObject.getJSONObject("request").getJSONObject("body").getString("raw");
                    endpointInfo += String.format(", BODY: \"%s\"", requestBody);
                    endpointInfoJson.put("body",requestBody);
                }

                endpointInfoMap.put(serviceName, endpointInfo);
                endpointInfoMapJson.put(serviceName, endpointInfoJson);
            }

            apiInfoMap.put(serviceName, endpointInfoMap);
            apiInfoMapJson.put(serviceName, endpointInfoMapJson);
        }

        return apiInfoMapJson;
    }
}
