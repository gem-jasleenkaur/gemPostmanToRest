package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.qa.gemini.payloads.FalseVariancePayloads;
import com.qa.gemini.pojo.StatusClassification.FalseVariance;
import com.qa.gemini.pojo.StatusClassification.VarianceFields;
import com.qa.gemini.utils.ApiUtils;
import io.cucumber.java.en.When;

import java.util.Map;

import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.GetFakeData.fakerInstance;
import static com.qa.gemini.utils.GetFakeData.generateTempTCRunId;

public class StatusClassificationControllerSteps {

    private StatusClassificationControllerSteps statusClassificationControllerSteps;

    @When("Set endpoint {string} method {string} and payload to create variance on testcase {string} level")
    public void setEndpointMethodAndPayloadToCreateVarianceOnTestcaseLevel(String endpoint, String method,
                                                                           String testcase) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        FalseVariance payload = FalseVariancePayloads.createVariancePayload(fakerInstance().lorem().sentence(),
                getMachineEpochTime(), testcase, "Exact",
                generateTempTCRunId(), getMachineEpochTime() + 100, "test");
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }

    @When("Set endpoint {string} method {string} to create variance with null {string} on {string} level")
    public void setEndpointMethodAndPayloadToCreateVarianceWithNullFields(String endpoint, String method, String field, String testcase) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");

        String reason = fakerInstance().lorem().sentence();
        Long startTime = getMachineEpochTime();
        String name = testcase;
        String match = "Exact";
        String tc_run_id = generateTempTCRunId();
        Long endDate = getMachineEpochTime() + 100;
        String category = "test";

        switch (field) {
            case "reason":
                reason=null;
                break;
            case "name":
               name=null;
                break;
            case "match":
               match=null;
                break;
            case "tc_run_id":
               tc_run_id=null;
                break;
            case "category":
               category=null;
                break;
            case "startTime":
                startTime=null;
                break;
            case "endDate":
               endDate= null;
                break;
        }
        FalseVariance payload = FalseVariancePayloads.createVariancePayload(reason, startTime, name, match, tc_run_id, endDate, category);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }

    @When("Set endpoint {string} method {string} to create variance with invalid {string} on {string} level")
    public void setEndpointMethodAndPayloadToCreateVarianceWithInvalidFields(String endpoint, String method, String field, String testcase) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");

        String reason = fakerInstance().lorem().sentence();
        Long startTime = getMachineEpochTime();
        String name = testcase;
        String match = "Exact";
        String tc_run_id = generateTempTCRunId();
        Long endDate = getMachineEpochTime() + 100;
        String category = "test";

        switch (field) {
            case "name":
                name="test";
                break;
            case "match":
                match="test";
                break;
            case "tc_run_id":
                tc_run_id="test";
                break;
            case "category":
                category="testing";
                break;
            case "startTime":
                startTime=getMachineEpochTime() + 10;
                break;
            case "endDate":
                endDate= getMachineEpochTime() - 20;
                break;
        }
        FalseVariance payload = FalseVariancePayloads.createVariancePayload(reason, startTime, name, match, tc_run_id, endDate, category);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }

    @When("Set endpoint {string} method {string} to create variance with empty {string} on {string} level")
    public void setEndpointMethodAndPayloadToCreateVarianceWithEmptyFields(String endpoint, String method, String field, String testcase) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");

        String reason = fakerInstance().lorem().sentence();
        Long startTime = getMachineEpochTime();
        String name = testcase;
        String match = "Exact";
        String tc_run_id = generateTempTCRunId();
        Long endDate = getMachineEpochTime() + 100;
        String category = "test";

        switch (field) {
            case "reason":
                reason="";
                break;
            case "name":
                name="";
                break;
            case "match":
                match="";
                break;
            case "tc_run_id":
                tc_run_id="";
                break;
            case "category":
                category="";
                break;
            case "startTime":
                startTime=null;
                break;
            case "endDate":
                endDate=null;
                break;
        }
        VarianceFields payload = FalseVariancePayloads.createVarianceFieldsPayload(reason, startTime, name, match, tc_run_id, endDate, category);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }

    @When("Set endpoint {string} method {string} to create variance")
    public void setEndpointMethodToCreateVarianceWithoutPayloadOnTestcaseLevel(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithNullPayload(endpoint, method, header);
        System.out.println(response.getStatus());
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }

    @When("Set endpoint {string} method {string} to create variance with empty payload")
    public void setEndpointMethodAndEmptyPayloadToCreateVarianceOnTestcaseLevel(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithEmptyPayload(endpoint, method, header);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }


    @When("Set endpoint {string} method {string} and param to get variance")
    public void setEndpointMethodAndParamToGetVariance(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "/";
        if (getJewelMapValue("varianceId") != null)
            param = param.concat((String) getJewelMapValue("varianceId"));
        else
            param = param.concat(Integer.MAX_VALUE + "");
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and payload to reset variance")
    public void setEndpointMethodAndPayloadToResetVariance(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "/";
        if (getJewelMapValue("varianceId") != null)
            param = param.concat((String) getJewelMapValue("varianceId"));
        else
            param = param.concat(Integer.MAX_VALUE + "");
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and payload to create variance on step {string} level")
    public void setEndpointMethodAndPayloadToCreateVarianceOnStepLevel(String endpoint, String method,
                                                                       String step) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        FalseVariance payload = FalseVariancePayloads.createVariancePayload(fakerInstance().lorem().sentence(),
                getMachineEpochTime(), step, "Exact", generateTempTCRunId(),
                getMachineEpochTime() + 100, "step");
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 200) {
            setJewelMapValue("varianceId", getResponseDataFor(response, "ID"));
        }
    }


}
