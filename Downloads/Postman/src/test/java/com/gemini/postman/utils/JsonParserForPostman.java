package com.gemini.postman.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParserForPostman {
    public static String suiteFilePath = "C:\\Users\\jasleen.multani\\Downloads\\jewel_UI\\Postman\\src\\test\\resources\\Demo.postman_collection.json";
    public static String envFilePath = "C:\\Users\\jasleen.multani\\Downloads\\jewel_UI\\Postman\\src\\test\\resources\\Test Environment.postman_environment (1).json";

    static LinkedHashMap<String, HashMap<Object,Object>> postman = new LinkedHashMap<>();
    public static void readJson()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(suiteFilePath));
            BufferedReader readerEnv = new BufferedReader(new FileReader(envFilePath));
            // Read the JSON data from the file
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            StringBuilder jsonStringBuilderEnv = new StringBuilder();
            String lineEnv;
            while ((line = reader.readLine()) != null)
            {
                jsonStringBuilder.append(line);
            }

            while ((lineEnv = readerEnv.readLine()) != null)
            {
                jsonStringBuilderEnv.append(lineEnv);
            }

        // Parse the JSON data
        String jsonString = jsonStringBuilder.toString();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

        String jsonStringForEnv = jsonStringBuilderEnv.toString();
        JsonParser parserForEnv = new JsonParser();
        JsonObject jsonObjectForEnv = parserForEnv.parse(jsonStringForEnv).getAsJsonObject();

            // Access the specific values you need
        JsonObject infoObject = jsonObject.getAsJsonObject("info");
        String postmanId = infoObject.get("_postman_id").getAsString();
        String name = infoObject.get("name").getAsString();
        String schema = infoObject.get("schema").getAsString();
        String exporterId = infoObject.get("_exporter_id").getAsString();

        // Print the values
        System.out.println("_postman_id: " + postmanId);
        System.out.println("Name: " + name);
        System.out.println("Schema: " + schema);
        System.out.println("_exporter_id: " + exporterId);

        JsonArray itemArray = jsonObject.getAsJsonArray("item");
        System.out.println("Number of requests are: " +itemArray.size());
        for(int iteration=0;iteration<itemArray.size();iteration++)
        {
            LinkedHashMap<Object,Object> request = new LinkedHashMap<>();
            System.out.println("name for request "+(iteration+1)+" is "+itemArray.get(iteration).getAsJsonObject().get("name"));
            String requestName = itemArray.get(iteration).getAsJsonObject().get("name").toString();

         //    request.put("Name",requestName);
         //   postman.put(itemArray.get(iteration).getAsJsonObject().get("name"));
            if(itemArray.get(iteration).getAsJsonObject().has("event"))
            {
                JsonArray eventArray = itemArray.get(iteration).getAsJsonObject().get("event").getAsJsonArray();
              //  request.put("event",eventArray);
                System.out.println("event for "+requestName+ " is: "+itemArray.get(iteration).getAsJsonObject().get("event"));
                for(int eventIteration=0;eventIteration<eventArray.size();eventIteration++)
                {
                    System.out.println("listen for event  "+(eventIteration+1)+" belonging to "+requestName+" is: "+eventArray.get(eventIteration).getAsJsonObject().get("listen"));
                    System.out.println("script for event "+(eventIteration+1)+" belonging to "+requestName+" is: "+eventArray.get(eventIteration).getAsJsonObject().get("script"));
                    request.put(eventArray.get(eventIteration).getAsJsonObject().get("listen"),eventArray.get(eventIteration).getAsJsonObject().get("script"));
                }
            }
            if(itemArray.get(iteration).getAsJsonObject().has("request"))
            {
                JsonObject requestObject = itemArray.get(iteration).getAsJsonObject().get("request").getAsJsonObject();
                System.out.println("Method for "+requestName+" is: "+requestObject.get("method"));
                request.put("method",requestObject.get("method"));
                System.out.println("Headers for "+requestName+" is: "+requestObject.get("header"));
                request.put("headers",requestObject.get("header"));
                if(requestObject.get("url").getAsJsonObject().size()>0)
                {
                    JsonObject urlObject = requestObject.get("url").getAsJsonObject();
                    System.out.println("Url for "+requestName+" is: "+urlObject.get("raw"));
                    request.put("Url",urlObject.get("raw"));
                    if (urlObject.get("raw").toString().contains("{{") && urlObject.get("raw").toString().contains("}}")) {

                        //replace
                        Pattern pattern = Pattern.compile("\\{\\{(.+?)}}");
                        Matcher matcher = pattern.matcher(urlObject.get("raw").toString());
                        String value = urlObject.get("raw").toString(); // Initialize value with the raw URL
                        while (matcher.find()) {
                            String placeholder = matcher.group(1); // Extract text between {{ and }}
                            // Here you can process the extracted placeholder text as needed
                            System.out.println("Extracted placeholder: " + placeholder);
                            // For this example, let's replace it with "abc"
                            for (int i = 0; i < jsonObjectForEnv.get("values").getAsJsonArray().size(); i++) {
                                if (jsonObjectForEnv.get("values").getAsJsonArray().get(i).getAsJsonObject().get("key").toString().replaceAll("\"", "").equals(placeholder)) {
                                    String replacedValue = jsonObjectForEnv.get("values").getAsJsonArray().get(i).getAsJsonObject().get("value").getAsString().replaceAll("\"", "");
                                    value = value.replace("{{" + placeholder + "}}", replacedValue);
                                    System.out.println("Replaced placeholder '" + placeholder + "' with value: " + replacedValue);
                                }
                            }
                        }
                        System.out.println("Replaced url from env for "+requestName+" is: "+value);
                        request.put("Url",value);
                    }
                    System.out.println("Protocol for "+requestName+" is: "+urlObject.get("protocol"));
                    request.put("Protocol",urlObject.get("protocol"));
                    System.out.println("Host array for "+requestName+" is: "+urlObject.get("host").getAsJsonArray());
                    request.put("Host",urlObject.get("host").getAsJsonArray());
                 /*   if(urlObject.get("host").getAsJsonArray().size()>0)
                    {
                        JsonArray hostArray = urlObject.get("host").getAsJsonArray();
                        for(int hostIteration=0;hostIteration<hostArray.size();hostIteration++)
                        {
                            System.out.println("host array for iteration "+hostIteration+"has this value "+hostArray.get(hostIteration));
                        }
                    }*/
                    System.out.println("Path array for "+requestName+" is: "+urlObject.get("path").getAsJsonArray());
                    request.put("Host",urlObject.get("path").getAsJsonArray());
               /*if(urlObject.get("path").getAsJsonArray().size()>0)
                    {
                        JsonArray pathArray = urlObject.get("path").getAsJsonArray();
                        for(int pathIteration=0;pathIteration<pathArray.size();pathIteration++)
                        {
                            System.out.println("path array for iteration "+pathIteration+"has this value "+pathArray.get(pathIteration));
                        }
                    }*/
                }
            }
            postman.put(requestName,request);
        }

        System.out.println(postman);

        } catch (IOException e) {
        e.printStackTrace();
    }
}
}
