package com.gemini.postman.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonFunctionsForEnvironment {

    public static void storePostmanJsonToSmallerJson() throws JsonProcessingException {
        String json = loadJsonFromFile("src/test/resources/UAT Domain.postman_environment 2.json");

        Map<String, List<JSONObject>> apiInfoMap = parseJson(json);

        // Write endpoint information to a single JSON file for each service
        for (Map.Entry<String, List<JSONObject>> serviceEntry : apiInfoMap.entrySet()) {
            String serviceName = serviceEntry.getKey();
            List<JSONObject> endpointInfoList = serviceEntry.getValue();

            // Combine all key-value pairs into a single JSONObject
            JSONObject combinedJson = new JSONObject();
            for (JSONObject endpointInfo : endpointInfoList) {
                for (String key : endpointInfo.keySet()) {
                    combinedJson.put(key, endpointInfo.get(key));
                }
            }

            // Create a directory for each API info
            String apiInfoDirectoryPath = "src/test/resources/environmentdata/";
            createOutputDirectory(apiInfoDirectoryPath);

            // Write the combined JSON to a file within the API info directory
            String fileName = "environmentvariables.json";
            writeJsonToFile(apiInfoDirectoryPath + "/" + fileName, serviceName, combinedJson);
        }
    }

    private static void createOutputDirectory(String directoryPath) {
       Path path = Paths.get(directoryPath);

            try {
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                } else {
                    System.out.println("Directory already exists: " + directoryPath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating output directory: " + directoryPath, e);
            }
        }


    private static void writeJsonToFile(String filePath, String serviceName, JSONObject jsonContent) {
        try {
            // Check if the file exists
            boolean fileExists = Files.exists(Paths.get(filePath));

            // Create a directory for each API info if it doesn't exist
            String apiInfoDirectoryPath = "src/test/resources/environmentdata/";
            createOutputDirectory(apiInfoDirectoryPath);

            // If the file exists, read existing content and append to it; otherwise, create a new file
            if (fileExists) {
                String existingContent = loadJsonFromFile(filePath);
                JSONObject existingJson = new JSONObject(existingContent);

                // Check if the service already exists in the JSON
                if (existingJson.has(serviceName)) {
                    JSONArray serviceArray = existingJson.getJSONArray(serviceName);
                    serviceArray.put(jsonContent);
                } else {
                    JSONArray newArray = new JSONArray();
                    newArray.put(jsonContent);
                    existingJson.put(serviceName, newArray);
                }

                Files.write(Paths.get(filePath), (existingJson.toString(2) + System.lineSeparator()).getBytes());
            } else {
                // If the file doesn't exist, create a new JSON object with the service name
                JSONObject newJson = new JSONObject();
                newJson.put(serviceName, new JSONArray().put(jsonContent));

                Files.write(Paths.get(filePath), (newJson.toString(2) + System.lineSeparator()).getBytes());
            }

            System.out.println("Data appended to file: " + filePath);
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

    private static Map<String, List<JSONObject>> parseJson(String jsonString) {
        Map<String, List<JSONObject>> apiInfoMapJson = new HashMap<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        String serviceName = jsonObject.getString("name");

        // Initialize the outer map outside the loop
        List<JSONObject> endpointInfoList = apiInfoMapJson.computeIfAbsent(serviceName, k -> new ArrayList<>());

        JSONArray valuesArray = jsonObject.getJSONArray("values");

        for (int i = 0; i < valuesArray.length(); i++) {
            JSONObject valueObject = valuesArray.getJSONObject(i);
            String key = valueObject.getString("key");
            String value = valueObject.getString("value");

            JSONObject endpointInfoJson = new JSONObject();
            endpointInfoJson.put(key, value);

            endpointInfoList.add(endpointInfoJson);
        }

        return apiInfoMapJson;
    }
}
