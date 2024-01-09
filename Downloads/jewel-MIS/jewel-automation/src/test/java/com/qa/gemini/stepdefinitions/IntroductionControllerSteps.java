package com.qa.gemini.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.ui.utils.DriverAction;
import com.qa.gemini.payloads.IntroductionControllerPayloads;
import com.qa.gemini.pojo.Introduction;
import com.qa.gemini.utils.ApiUtils;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Map;

import static com.qa.gemini.utils.ApiUtils.verifyKeyIsNotPresent;
import static com.qa.gemini.utils.ApiUtils.verifyKeyPresent;
import static com.qa.gemini.utils.CommonMethods.getJewelMapValue;
import static com.qa.gemini.utils.CommonMethods.setJewelMapValue;

public class IntroductionControllerSteps {


    @When("^User sets endpoint \"(.*)\" method \"(.*)\" and \"(.*)\" as \"(.*)\" parameter$")
    public void getListOfIntro(String endpoint, String method, String paramValue, String paramKey) {
        String param = "?";
        param = param.concat(paramKey).concat("=").concat(paramValue);
        Response response = ApiUtils.hitApiWithParam(endpoint, method, param);
        setJewelMapValue("response", response);
    }
    private static final Logger logger = LogManager.getLogger(DriverAction.class);
    Request request = new Request();

    @Before
    public void featureBeforeHooks() {
        this.request = new Request();
    }

    @When("^User sets \"(.*)\" as path")
    public void setURL(String path) {
        try {
            this.request.setpath(path);
            GemTestReporter.addTestStep("Set path", "path : " + path, STATUS.INFO);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Set path", "Failed to set path : " + path, STATUS.FAIL);
            logger.info("Failed to set path : " + path);
            logger.fatal(e.getMessage());
        }
    }

    @When("^User sets \"(.*)\" as method")
    public void setMethod(String method) {
        try {
            this.request.setMethod(method);
            GemTestReporter.addTestStep("Set method", "method : " + method, STATUS.INFO);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Set method", "Failed to set method : " + method, STATUS.FAIL);
            logger.info("Failed to set method : " + method);
            logger.fatal(e.getMessage());
        }
    }

    @And("^User sets \"(.*)\" key as \"(.*)\" value$")
    public void userSetsKeyAsValue(String paramKey, String paramValue) {
        try {
            this.request.setParameter(paramKey, paramValue);
            this.request.setURL();
            GemTestReporter.addTestStep("Set parameters", "Parameter key : " + paramKey + " \n Parameter value : " + paramValue + " \n Updated url : " + this.request.getURL(), STATUS.INFO);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Failed to set parameters", "Parameter key : " + paramKey + " \n Parameter value : " + paramValue, STATUS.INFO);
            logger.info("Failed to update parameter", "Parameter key : " + paramKey + " \n Parameter value : " + paramValue);
            logger.info(e.getMessage());
        }
    }

    @When("^User sets endpoint \"(.*)\" and sets \"(.*)\" as method$")
    public void getListOfAllIntro(String endpoint, String method) {
        Response response = ApiUtils.hitApi(endpoint, method);
        setJewelMapValue("response", response);
    }

    @When("^User sets endpoint \"(.*)\" method \"(.*)\" for \"(.*)\" payload$")
    public void createIntro(String endpoint, String method, String paramValue) {
            JsonObject data = Json.createObjectBuilder().add("title", "Sample insertion to check API").build();
            Introduction payload = IntroductionControllerPayloads.createIntroductionPayload(paramValue, convertJsonObjectToMap(data));
            Response response = ApiUtils.hitApiWithPayload(endpoint, method, payload);
            setJewelMapValue("response", response);

    }

    @When("^User sets endpoint \"(.*)\" method \"(.*)\" with empty \"(.*)\" key in payload$")
    public void createIntroForEmptyGemName(String endpoint, String method,String paramKey) {
        Introduction payload = null;
        if(paramKey.equals("gemName"))
        {
            JsonObject data = Json.createObjectBuilder().add("title", "Sample insertion to check API").build();
            payload = IntroductionControllerPayloads.createIntroductionPayload("", convertJsonObjectToMap(data));
        }
        else{
            JsonObject data = Json.createObjectBuilder().build();
            payload = IntroductionControllerPayloads.createIntroductionPayload(paramKey, convertJsonObjectToMap(data));
        }
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, payload);
        setJewelMapValue("response", response);

    }

    @When("^User sets endpoint \"(.*)\" method \"(.*)\" without \"(.*)\" key$")
    public void createIntroWithoutKey(String endpoint, String method, String paramName) {
        JsonObject data = Json.createObjectBuilder().add("title", "Sample insertion to check API").build();
        Introduction payload = IntroductionControllerPayloads.createIntroductionPayloadWithoutGemName(paramName, convertJsonObjectToMap(data));
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, payload);
        setJewelMapValue("response", response);

    }

    @When("^User sets endpoint \"(.*)\" method \"(.*)\" with invalid payload$")
    public void createInvalidIntro(String endpoint, String method) {
        JsonObject data = Json.createObjectBuilder().add("title", "Sample insertion to check API").build();
        Introduction payload = IntroductionControllerPayloads.createIntroductionPayload("", convertJsonObjectToMap(data));
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, payload);
        setJewelMapValue("response", response);

    }

    @Then("^User verifies \"(.*)\" is present in response body$")
    public void userVerifiesIntroKeys(String paramValue) {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyPresent(response, paramValue);

    }

    @Then("^User verifies \"(.*)\" is not present in response body$")
    public void userVerifiesKeyIsNotPresent(String paramValue) {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyIsNotPresent(response, paramValue);

    }

    @Then("^User verifies (.*) is the response code$")
    public void verifyResponseCode(int responseCode) {
        try {
            Response response = (Response) getJewelMapValue("response");
            ApiUtils.verifyStatusCode(responseCode, response.getStatus());
        } catch (Exception e) {
            GemTestReporter.addTestStep("Status Verification", "Something went wrong.", STATUS.ERR);
        }
    }

    private Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        // Convert JsonObject to Map
        // This might depend on the JSON library you are using (javax.json, Jackson, etc.)
        // Here's an example using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(jsonObject, new TypeReference<Map<String, Object>>() {});
    }
}
