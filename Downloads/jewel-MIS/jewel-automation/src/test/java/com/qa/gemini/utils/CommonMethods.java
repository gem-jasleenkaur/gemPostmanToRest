package com.qa.gemini.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.utils.ProjectConfigData;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class CommonMethods {

    private static Map<Object, Object> jewel_map = new ConcurrentHashMap<>();

    public static String getURL(String endpointKey) {
        String baseURL = ProjectConfigData.getProperty("base_url");
        String endpoint = EndpointsReader.getInstance().getEndpointValue(endpointKey);
        return baseURL + endpoint;
    }

    public static String getURL(String endpointKey, String param) {
        String baseURL = ProjectConfigData.getProperty("base_url");
        String endpoint = EndpointsReader.getInstance().getEndpointValue(endpointKey);
        return baseURL + endpoint + param;
    }

    public static String getResponseDataFor(Response response, String field) {
        return response.getResponseBodyJson().getAsJsonObject().get(
                "data").getAsJsonObject().get(field).getAsString();
    }

    public static int getResponseDataSize(Response response, String field) {
        return response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonObject().get(
                field).getAsJsonArray().size();
    }

    public static String getResponseMessageFor(Response response, String field) {
        try {
            if (response.getResponseBodyJson() != null)
                return response.getResponseBodyJson().getAsJsonObject().get(field).getAsString();
            return response.getJsonObject().get("responseError").getAsJsonObject().get(field).getAsString();
        } catch (Exception e) {
            GemTestReporter.addTestStep("Response message", "field is not present in response.",
                    STATUS.FAIL);
            return null;
        }
    }

    public static String getResponseMessageForData(Response response, String field) {
        return response.getResponseBodyJson().getAsJsonObject().get("data").getAsString();
    }

    public static String getResponseMessageForErrors(Response response, String field) {
        return response.getResponseBodyJson().getAsJsonObject().get("errors").getAsJsonArray()
                .get(0).getAsJsonObject().get(field).getAsString();
    }

    public static String getResponseMessageErrorFor(Response response, String field) {
        JSONObject json = new JSONObject(response.getErrorMessage());
        return json.getString(field);
    }

    public static String getResponseErrorMessageFor(Response response, String field) {
        return response.getJsonObject().get("responseError").getAsJsonObject().get(field).getAsString();
    }

    public static Map<Object, Object> getJewel_map() {
        return jewel_map;
    }

    public static Object getJewelMapValue(Object key) {
        return jewel_map.get(key);
    }

    public static void setJewelMapValue(Object key, Object value) {
        jewel_map.put(key, value);
    }

    public static String getTestcaseID(String response) {
        JSONObject json = new JSONObject(response);
        JSONObject json2 = (JSONObject) json.get("data");
        JSONArray json3 = (JSONArray) json2.get("data");
        JSONObject json4 = (JSONObject) json3.get(0);
        JSONObject json5 = (JSONObject) json4.get("Testcase ID");
        return Integer.toString((Integer) json5.get("sortValue"));
    }

    public static Long getMachineEpochTime() {
        return System.currentTimeMillis(); // Divide by 1000 to convert to seconds
    }

    public static String generateSRunId(String project_name, String env) {
        Faker faker = new Faker();
        return project_name.concat("_").concat(env).concat("_").concat(faker.regexify("[A-Za-z0-9]{15}"));
    }

    public static String generateTcRunId() {
        Faker faker = new Faker();
        String tcRunId = "Feature".concat(faker.regexify("[A-Za-z]{3}"));
        return tcRunId.concat("_").concat("Scenario" + faker.regexify("[A-Za-z]{3}")).concat("_").concat(faker.regexify("[A-Za-z0-9]{15}"));
    }

    public static Boolean generateRandomStatus() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public static List<Object> generateSteps(String name, String description, String status) {
        List<Object> list = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("Step Name", name);
        json.put("Step Description", description);
        json.put("status", status);
        list.add(json);
        return list;
    }

    public static String generatePid() {
        Random random = new Random();
        int invalidNumber = random.nextInt(10000);
        return Integer.toString(invalidNumber);
    }


    public static String decodeMD5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(string.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder actualMD5Hash = new StringBuilder(no.toString(16));
            while (actualMD5Hash.length() < 32) {
                actualMD5Hash.insert(0, "0");
            }
            return actualMD5Hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to decode string using MD5.");
        }
    }

    public static void createFolder(String name) {
        String folderPath = System.getProperty("user.dir") + "/src/test/resources/";
        File folder = new File(folderPath + name);
        boolean success = folder.mkdirs();
        if (!success) {
            System.out.println("Failed to create folder:");
        }
    }

    public static String createFile(String fileName, String ext) {
        String folderPath = System.getProperty("user.dir") + "/src/test/resources/files";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            createFolder("files");
        }
        Random random = new Random();
        fileName = fileName + "." + ext;
        File file = new File(folder, fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] randomBytes = new byte[1024];
            random.nextBytes(randomBytes);
            fos.write(randomBytes);
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file - " + fileName);
        }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("Failed to delete file: " + filePath);
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    public static String maskingPayload(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);
            if (jsonNode.has("password")) {
                if (!(jsonNode.get("password").toString().equals("\"\"")))
                    ((ObjectNode) jsonNode).put("password", "********");
            }
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Masking Payload", "Fail to generate masked payload",
                    STATUS.ERR);
            return payload;
        }
    }

    public static String maskingTokenHeader(String token) {
        if (token.isEmpty()) {
            return "-";
        }
        String prefix = token.substring(0, 5);
        String suffix = token.substring(token.length() - 5);
        String maskedMiddle = "********";
        return prefix + maskedMiddle + suffix;
    }

    public static Map<String, String> maskingBridgeTokenHeader(Map<String, String> header) {
        Map<String, String> temp = header;
        if (temp.containsKey("bridgeToken")) {
            temp.put("bridgeToken", "********");
        }
        return temp;
    }

    public static String maskingResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.has("data")) {
                JsonNode dataNode = jsonNode.get("data");
                if (dataNode.has("bridgeToken")) {
                    String bridgeToken = dataNode.get("bridgeToken").asText();
                    String maskedBridgeToken = maskingTokenHeader(bridgeToken);
                    ((ObjectNode) dataNode).put("bridgeToken", maskedBridgeToken);
                }
                if (dataNode.has("token")) {
                    String token = dataNode.get("token").asText();
                    String maskedToken = maskingTokenHeader(token);
                    ((ObjectNode) dataNode).put("token", maskedToken);
                }
                if (dataNode.has("password")) {
                    ((ObjectNode) dataNode).put("password", "********");
                }
            } else {
                if (jsonNode.has("bridgeToken")) {
                    String bridgeToken = jsonNode.get("bridgeToken").asText();
                    String maskedBridgeToken = maskingTokenHeader(bridgeToken);
                    ((ObjectNode) jsonNode).put("bridgeToken", maskedBridgeToken);
                }
                if (jsonNode.has("token")) {
                    String token = jsonNode.get("token").asText();
                    String maskedToken = maskingTokenHeader(token);
                    ((ObjectNode) jsonNode).put("token", maskedToken);
                }
                if (jsonNode.has("password")) {
                    ((ObjectNode) jsonNode).put("password", "********");
                }
            }
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Masking Response", "Fail to generate masked response",
                    STATUS.ERR);
            return response;
        }
    }

    public static Document getXMLBody(String str) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(str)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        decodeMD5("abcd");
        // 6acc1b54b6196c3876513001fe11e580
//        String filen = createFile(generateFileName(), "txt");
//        deleteFile(filen);
//        System.out.println(encodedLoginPayload(""));
    }
}
