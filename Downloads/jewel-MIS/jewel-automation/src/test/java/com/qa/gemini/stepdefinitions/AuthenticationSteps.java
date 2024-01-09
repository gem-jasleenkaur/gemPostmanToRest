package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.qa.gemini.pojo.Authentication;
import com.qa.gemini.utils.ApiUtils;
import com.qa.gemini.utils.DataFileReader;
import com.qa.gemini.utils.TokenManger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.HashMap;
import java.util.Map;

import static com.qa.gemini.payloads.AuthenticatePayloads.authenticationPayload;
import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.GetFakeData.generateInvalidToken;

public class AuthenticationSteps {

    @Given("Step {string}")
    public void step(String stepName) {
        GemTestReporter.addTestStep(stepName, "Request for " + stepName, STATUS.INFO);
    }

    @And("User authenticate as {string} user")
    public void userAuthenticateAsUser(String userType) {
        String user;
        switch (userType.toLowerCase()) {
            case "admin":
                user = "admin_user";
                break;
            case "superadmin":
                user = "super_admin_user";
                break;
            case "new":
                user = "new_user";
                break;
            default:
                user = "normal_user";
        }
        setJewelMapValue("usertype", user);
        Authentication authentication = authenticationPayload(user);
        String token = TokenManger.getToken(authentication);
        setJewelMapValue("token", token);
        setHeaderMap();
    }

    @Then("Verify status code {int}")
    public void verifyStatusCodeStatus_(int expected_status) {
        try {
            Response response = (Response) getJewelMapValue("response");
            ApiUtils.verifyStatusCode(expected_status, response.getStatus());
        } catch (Exception e) {
            GemTestReporter.addTestStep("Status Verification", "Something went wrong.", STATUS.ERR);
        }
    }

    private void setHeaderMap() {
        Map<String, String> header = new HashMap<>();
        String token = (String) getJewelMapValue("token");
        header.put("Authorization", "Bearer " + token);
        GemTestReporter.addTestStep("Bearer Token", maskingTokenHeader(token), STATUS.INFO);
        setJewelMapValue("header", header);
    }

    private void setBridgeTokenHeaderMap() {
        Map<String, String> header = new HashMap<>();
        String bridge_token = (String) getJewelMapValue("bridge_token");
        header.put("username", DataFileReader.getInstance()
                .getUsername((String) getJewelMapValue("usertype")));
        header.put("bridgeToken", bridge_token);
        Map<String, String> masked = new HashMap<>(header);
        GemTestReporter.addTestStep("Header", "Header: " + maskingBridgeTokenHeader(masked), STATUS.INFO);
        setJewelMapValue("bt_header", header);
    }

    @Given("User tries to authenticate as {string} token")
    public void userTriesToAuthenticateAsToken(String tokenType) {
        String token = "";
        GemTestReporter.addTestStep("User Authentication with " + tokenType + " Token", "User tries to " +
                "authenticate with " + tokenType + " token", STATUS.INFO);
        if (tokenType.equalsIgnoreCase("invalid")) {
            token = generateInvalidToken();
        }
        setJewelMapValue("token", token);
        setHeaderMap();
    }

    @And("Set the username and bridge token header")
    public void setTheUsernameAndBridgeTokenHeader() {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String bridge_token = TokenManger.getBridgeToken(header);
        setJewelMapValue("bridge_token", bridge_token);
        setBridgeTokenHeaderMap();
    }

    @And("Set the username and bridge token header as without {string}")
    public void setTheUsernameAndBridgeTokenHeaderAsWithoutAuth(String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String bridge_token = TokenManger.getBridgeToken(header);
        setJewelMapValue("bridge_token", bridge_token);
        setBridgeTokenHeaderMap();
        Map<String, String> bt_header = (Map<String, String>) getJewelMapValue("bt_header");
        if (field.equalsIgnoreCase("all")) {
            bt_header.remove("username");
            bt_header.remove("bridgeToken");
        } else {
            bt_header.remove(field);
        }
    }
}
