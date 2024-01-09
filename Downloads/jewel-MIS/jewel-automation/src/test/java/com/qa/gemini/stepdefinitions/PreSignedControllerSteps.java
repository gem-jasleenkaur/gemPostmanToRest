package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.qa.gemini.payloads.PreSignedPayloads;
import com.qa.gemini.pojo.PreSigned;
import com.qa.gemini.utils.ApiUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.GetFakeData.*;

public class PreSignedControllerSteps {
    @And("Set endpoint {string} method {string} and with pre signed payload to generate {int} url")
    public void setEndpointMethodAndWithPreSignedPayloadToGenerateUrl(String endpoint, String method,
                                                                      int num) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<String> files = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            files.add(generateFileName() + ".png");
//            files.add(getFakeImage(createFile(generateFileName(), "png")));
        }
        PreSigned preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                generateTempSRunId(), "Protected");
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, preSigned);
        setJewelMapValue("response", response);

        if (response.getStatus() == 200) {
            JsonArray res_list = response.getResponseBodyJson().getAsJsonObject().get("data")
                    .getAsJsonArray();
            List<String> urls = new LinkedList<>();
            for (JsonElement obj : res_list) {
                for (String file : files) {
                    if (obj.getAsJsonObject().has(file)) {
                        urls.add(obj.getAsJsonObject().get(file).getAsString());
                    }
                }
            }
            setJewelMapValue("preSigned_file_urls", urls);
        }
    }

    @Then("Verify the number of file as {int} in response")
    public void verifyTheNumberOfFileAsNumInResponse(int num) {
        try {
            Response response = (Response) getJewelMapValue("response");
            int expected_num = response.getResponseBodyJson().getAsJsonObject().get("data")
                    .getAsJsonArray().size();
            if (expected_num == num) {
                GemTestReporter.addTestStep("File Count Verification", "Expected Count :"
                        + expected_num + ", Actual Count :" + num, STATUS.PASS);
            } else {
                GemTestReporter.addTestStep("File Count Verification", "Expected Count :"
                        + expected_num + ", Actual Count :" + num, STATUS.FAIL);
            }
        } catch (IllegalStateException e) {
            GemTestReporter.addTestStep("Response message", "field is not present in response",
                    STATUS.FAIL);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Response message", "invalid response",
                    STATUS.FAIL);
        }
    }

    @And("Hit the each url with file to upload and verify status code {int}")
    public void hitTheEachUrlWithFileToUpload(int statusCode) {
        List<String> urls = (List<String>) getJewelMapValue("preSigned_file_urls");
        for (String url : urls) {
            String filePath = getFakeImage(createFile(generateFileName(), "png"));
            Response response = new Response();
            String method = "Put";
            try {
                Request request = new Request();
                GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
                request.setURL(url);
                request.setFilePath(filePath);
//                request.setContentType()
                // TODO : GEM-JAR binary upload
                request.setMethod(method);
                setJewelMapValue("request", request);
                response = ApiInvocation.handleRequest(request);
                System.out.println("RES - " + response);
                GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
                GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
                if ((response.getResponseBody()) != null) {
                    GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
                } else {
                    GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
                }
                if ((response.getErrorMessage()) != null) {
                    GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
                }
            } catch (Exception e) {
                GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            }
            System.out.println(response.getStatus());
            deleteFile(filePath);
        }
    }

    @Then("Set endpoint {string} method {string} and with empty {string} payload to generate url")
    public void setEndpointMethodAndWithEmptyPayloadToGenerateUrl(String endpoint, String method,
                                                                  String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<String> files = new LinkedList<>();
        PreSigned preSigned = null;
        switch (field) {
            case "file":
                preSigned = PreSignedPayloads.preSignedPayload(Collections.emptyList(), generateFolderName(),
                        generateTempSRunId(), "Protected");
                break;
            case "fileName":
                files.add("");
                preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                        generateTempSRunId(), "Protected");
                break;
            case "s_run_id":
                files.add(generateFileName() + ".png");
                preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                        "", "Protected");
                break;
            case "tag":
                files.add(generateFileName() + ".png");
                preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                        generateTempSRunId(), "");

        }
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, preSigned);
        setJewelMapValue("response", response);
    }

    @Then("Set endpoint {string} method {string} and with invalid {string} payload to generate url")
    public void setEndpointMethodAndWithInvalidPayloadToGenerateUrl(String endpoint, String method,
                                                                    String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<String> files = new LinkedList<>();
        files.add(generateFileName() + ".png");
        PreSigned preSigned = null;
        if (field.equalsIgnoreCase("s_run_id")) {
            preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                    generateInvalidSRunId(), "Protected");
        } else if (field.equalsIgnoreCase("tag")) {
            preSigned = PreSignedPayloads.preSignedPayload(files, generateFolderName(),
                    generateTempSRunId(), "XYZ");
        }

        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, preSigned);
        setJewelMapValue("response", response);
    }

    @When("Upload file using expired pre-signed url using {string}")
    public void uploadFileUsingExpiredPreSignedUrlUsing(String method) {
        String url = getExpiredPreSignedURL();
        String filePath = getFakeImage(createFile(generateFileName(), "png"));
        Response response = new Response();
        try {
            Request request = new Request();
            GemTestReporter.addTestStep("Url for " + method.toUpperCase() + " Request", url, STATUS.INFO);
            request.setURL(url);
            request.setMethod(method);
            setJewelMapValue("request", request);
            response = ApiInvocation.handleRequest(request);
            setJewelMapValue("response", response);
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Executed Successfully", STATUS.PASS);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
            if ((response.getResponseBody()) != null) {
                GemTestReporter.addTestStep("Response Body", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep("Response Body", "No-Response", STATUS.INFO);
            }
            if ((response.getErrorMessage()) != null) {
                GemTestReporter.addTestStep("Error Message", response.getErrorMessage(), STATUS.INFO);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep(method.toUpperCase() + " Request Verification ", method.toUpperCase() + " Request Did not Executed Successfully", STATUS.FAIL);
            GemTestReporter.addTestStep("Response Message", response.getResponseMessage(), STATUS.INFO);
        }
    }
}
