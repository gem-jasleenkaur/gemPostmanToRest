package com.qa.gemini.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.qa.gemini.pojo.Authentication;

import java.util.Map;

import static com.qa.gemini.utils.CommonMethods.*;

public class TokenManger {

    public static String getToken(Authentication auth) {
        String token = null;
        try {
            GemTestReporter.addTestStep("User Authentication", "User login with username - \"" +
                    auth.getUsername() + "\"", STATUS.INFO);
            String url = getURL("login");
            GemTestReporter.addTestStep("Url for authentication", url, STATUS.INFO);
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(auth);
            GemTestReporter.addTestStep("Payload ", maskingPayload(payload), STATUS.INFO);
            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);
            Response response = ApiInvocation.handleRequest(request);
            token = response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonObject().get(
                    "token").getAsString();
            String companyName = response.getResponseBodyJson().getAsJsonObject().get(
                    "data").getAsJsonObject().get("company").getAsString();
            setJewelMapValue("company", companyName);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Auth Token", "Some error occurred while generating token",
                    STATUS.FAIL);
            e.printStackTrace();
        }
        return token;
    }

    public static String getBridgeToken(Map<String, String> header) {
        String bridge_token = null;
        try {
//            String env = ProjectConfigData.getProperty("environment");
//            String url = env.equalsIgnoreCase("beta") ?
//                    "https://apis-beta.gemecosystem.com/bridge/token" :
//                    "https://apis.gemecosystem.com/bridge/token";
            String url = getURL("getBridgeToken");
            GemTestReporter.addTestStep("Url of the authentication", url, STATUS.INFO);
            Request request = new Request();
            request.setURL(url);
            request.setMethod("Get");
            if (!header.isEmpty()) {
                request.setHeaders(header);
            }
            Response response = ApiInvocation.handleRequest(request);
            bridge_token = response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonObject().get(
                    "bridgeToken").getAsString();
        } catch (Exception e) {
            GemTestReporter.addTestStep("Auth Bridge Token",
                    "Some error occurred while generating bridge token", STATUS.FAIL);
            e.printStackTrace();
        }
        return bridge_token;
    }
}
