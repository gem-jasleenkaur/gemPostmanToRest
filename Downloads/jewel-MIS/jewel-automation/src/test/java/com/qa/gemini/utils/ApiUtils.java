package com.qa.gemini.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;

import java.util.List;
import java.util.Map;

import static com.qa.gemini.utils.CommonMethods.*;

public class ApiUtils {

    /**
     * Method to hit API which accepts API endpoint and method type.
     *
     * @param endpoint api endpoint
     * @param method   request method type
     * @return response
     */
    public static Response hitApi(String endpoint, String method) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url of the test case", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, method type and query params.
     *
     * @param endpoint api endpoint
     * @param method   request method type
     * @param param    query parameters
     * @return response
     */
    public static Response hitApiWithParam(String endpoint, String method, String param) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint, param);
            GemTestReporter.addTestStep("Url of the test case", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, method type and payload as Object.
     *
     * @param endpoint      api endpoint
     * @param method        request method type
     * @param payloadObject request body as Object
     * @return response
     */
    public static Response hitApiWithPayload(String endpoint, String method, Object payloadObject) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (payloadObject != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String payload = objectMapper.writeValueAsString(payloadObject);
                request.setRequestPayload(payload);
                if (payload.contains("password") || payload.contains("Password")) {
                    GemTestReporter.addTestStep("Payload ", maskingPayload(payload), STATUS.INFO);
                } else {
                    GemTestReporter.addTestStep("Payload ", payload, STATUS.INFO);
                }
            }
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, method type and empty payload
     *
     * @param endpoint      api endpoint
     * @param method        request method type
     * @return response
     */
    public static Response hitApiWithEmptyPayload(String endpoint, String method, Map<String,String> headers) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            request.setHeaders(headers);
            String payload="{}";
            request.setRequestPayload(payload);
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    public static Response hitApiWithNullPayload(String endpoint, String method, Map<String,String> headers) {
        Response response = new Response();
        try {
            String payload = " ";
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            request.setHeaders(headers);
//            request.setContentType("text/plain");
           request.setRequestPayload(payload);
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit the same API which will be hit previously.
     *
     * @return response
     */
    public static Response hitPreviousApiAgain() {
        Response response = new Response();
        String method = "";
        try {
            Request request = (Request) getJewelMapValue("request");
            String url = request.getURL();
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            method = request.getMethod();
            if (request.getRequestPayload() != null) {
                GemTestReporter.addTestStep("Payload ", request.getRequestPayload(), STATUS.INFO);
            }
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, method type, headers and payload as Object.
     *
     * @param endpoint      api endpoint
     * @param method        request method type
     * @param headers       request header which contains the token
     * @param payloadObject request body as Object
     * @return response
     */
    public static Response hitApiWithTokenAndPayload(String endpoint, String method,
                                                     Map<String, String> headers, Object payloadObject) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }
            if (payloadObject != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String payload = objectMapper.writeValueAsString(payloadObject);
                request.setRequestPayload(payload);
                if (payload.contains("password") || payload.contains("Password")) {
                    GemTestReporter.addTestStep("Payload ", maskingPayload(payload), STATUS.INFO);
                } else {
                    GemTestReporter.addTestStep("Payload ", payload, STATUS.INFO);
                }
            }
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            System.out.println(response.getStatus()+"--"+response.getResponseBody());
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, query params, method type, headers and payload as Object.
     *
     * @param endpoint      api endpoint
     * @param param         query parameters
     * @param method        request method type
     * @param headers       request header which contains the token
     * @param payloadObject request body as Object
     * @return response
     */
    public static Response hitApiWithParamTokenAndPayload(String endpoint, String param, String method,
                                                          Map<String, String> headers, Object payloadObject) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint, param);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }
            if (payloadObject != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String payload = objectMapper.writeValueAsString(payloadObject);
                request.setRequestPayload(payload);
                if (payload.contains("password") || payload.contains("Password")) {
                    GemTestReporter.addTestStep("Payload ", maskingPayload(payload), STATUS.INFO);
                } else {
                    GemTestReporter.addTestStep("Payload ", payload, STATUS.INFO);
                }
            }
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, method type, headers and payload as Object.
     *
     * @param endpoint api endpoint
     * @param method   request method type
     * @param headers  request header which contains the token
     * @return response
     */
    public static Response hitApiWithToken(String endpoint, String method, Map<String, String> headers) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, query params, method type, headers and payload as Object.
     *
     * @param endpoint api endpoint
     * @param param    query parameters
     * @param method   request method type
     * @param headers  request header which contains the token
     * @return response
     */
    public static Response hitApiWithParamAndToken(String endpoint, String param, String method,
                                                   Map<String, String> headers) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint, param);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to hit API which accepts API endpoint, query params, method type, headers and payload as Object.
     *
     * @param endpoint api endpoint
     * @param param    query parameters
     * @param method   request method type
     * @param headers  request header which contains the token
     * @param files    files to upload
     * @return response
     */
    public static Response hitApiWithParamTokenAndFiles(String endpoint, String param, String method,
                                                        Map<String, String> headers, List<String> files) {
        Response response = new Response();
        try {
            Request request = new Request();
            String url = getURL(endpoint, param);
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            if (!headers.isEmpty()) {
                request.setHeaders(headers);
            }

            String filePaths = files.get(0).substring(files.get(0).lastIndexOf("\\") + 1);
            String[] filePathArray = new String[files.size() - 1];
            for (int i = 1; i < files.size(); i++) {
                filePathArray[i - 1] = files.get(i);
                filePaths += ", " + files.get(i).substring(files.get(i).lastIndexOf("\\") + 1);
            }
            request.setFilePath(files.get(0), filePathArray);
            GemTestReporter.addTestStep("Attached file for upload", filePaths.trim(), STATUS.INFO);
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                printResponseBody(response.getResponseBody());
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
        }
        return response;
    }

    /**
     * Method to verify the status code
     *
     * @param expected expected status code
     * @param actual   actual status code
     */
    public static void verifyStatusCode(int expected, int actual) {
        if (expected == actual) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.PASS);
        } else {
            GemTestReporter.addTestStep("Status Verification", "Expected Status :" + expected + ",<br>Actual :" + actual, STATUS.FAIL);
        }
    }

    /**
     * Method to verify the response string
     *
     * @param step     to specify the response body key
     * @param expected expected string
     * @param actual   actual string
     */
    public static void verifyResponseString(String step, String expected, String actual) {
        step = "Response " + (step.charAt(0) + "").toUpperCase() + step.substring(1);
        if (expected.equalsIgnoreCase(actual)) {
            GemTestReporter.addTestStep(step + " Verification",
                    "Expected :" + expected + ",<br>Actual :" + actual, STATUS.PASS);
        } else {
            GemTestReporter.addTestStep(step + " Verification",
                    "Expected :" + expected + ",<br>Actual :" + actual, STATUS.FAIL);
        }
    }

    public static void verifyKeyPresent(Response response, String key) {
        try {
            if (response.getResponseBodyJson().getAsJsonObject().has("data")) {
                if (response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonObject()
                        .has(key)) {
                    GemTestReporter.addTestStep("Key Verification", key + " key is present.",
                            STATUS.PASS);
                } else {
                    GemTestReporter.addTestStep("Key Verification", key + "  key is not present.",
                            STATUS.FAIL);
                }
            } else {
                if (response.getResponseBodyJson().getAsJsonObject().has(key)) {
                    GemTestReporter.addTestStep("Key Verification", key + " key is present.",
                            STATUS.PASS);
                } else {
                    GemTestReporter.addTestStep("Key Verification", key + "  key is not present.",
                            STATUS.FAIL);
                }
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Key Verification Error", "response is not valid.",
                    STATUS.FAIL);
        }
    }

    public static void verifyKeyIsNotPresent(Response response, String key) {
        try {
            if (response.getResponseBodyJson().getAsJsonObject().has("data")) {
                if (response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonObject()
                        .has(key)) {
                    GemTestReporter.addTestStep("Key Verification", key + " key is present.",
                            STATUS.PASS);
                } else {
                    GemTestReporter.addTestStep("Key Verification", key + "  key is not present.",
                            STATUS.FAIL);
                }
            } else {
                if (response.getResponseBodyJson().getAsJsonObject().has(key)) {
                    GemTestReporter.addTestStep("Key Verification", key + " key is present.",
                            STATUS.PASS);
                } else {
                    GemTestReporter.addTestStep("Key Verification", key + "  key is not present.",
                            STATUS.FAIL);
                }
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Key Verification Error", "response is not valid.",
                    STATUS.FAIL);
        }
    }

    public static void printResponseBody(String response_body) {
        if (response_body.contains("bridgeToken") || response_body.contains("token") ||
                response_body.contains("password")) {
            GemTestReporter.addTestStep("Response Body", maskingResponse(response_body),
                    STATUS.INFO);
        } else {
            GemTestReporter.addTestStep("Response Body", response_body, STATUS.INFO);
        }
    }

    public static void verifyResponseCode(String response_body) {
        if (response_body.contains("bridgeToken") || response_body.contains("token") ||
                response_body.contains("password")) {
            GemTestReporter.addTestStep("Response Body", maskingResponse(response_body),
                    STATUS.INFO);
        } else {
            GemTestReporter.addTestStep("Response Body", response_body, STATUS.INFO);
        }
    }


}

