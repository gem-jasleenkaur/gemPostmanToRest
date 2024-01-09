package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.gemini.generic.utils.ProjectConfigData;
import com.qa.gemini.utils.ApiUtils;
import com.qa.gemini.utils.DataFileReader;
import io.cucumber.java.en.And;

import java.util.Map;

import static com.qa.gemini.utils.ApiUtils.verifyKeyPresent;
import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.Params.*;

public class AuthTokenControllerSteps {
    @And("Set endpoint {string} method {string} to change bridge token")
    public void setEndpointMethodToChangeBridgeToken(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithToken(endpoint, method, header);
        setJewelMapValue("response", response);
    }

    @And("Verify user change token response body")
    public void verifyUserChangeTokenResponseBody() {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyPresent(response, "bridgeToken");
    }

    @And("Verify user get token response body")
    public void verifyUserGetTokenResponseBody() {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyPresent(response, "bridgeToken");
    }

    @And("Set endpoint {string} method {string} to get bridge token")
    public void setEndpointMethodToGetBridgeToken(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithToken(endpoint, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} to get user status")
    public void setEndpointMethodToGetUserStatus(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        String username = (String) getJewelMapValue("username");
        if (username == null || username.isEmpty()) {
            username = ProjectConfigData.getProperty("jewel_user");
            username = username != null ? username :
                    DataFileReader.getInstance().getUsername((String) getJewelMapValue("usertype"));
        }
        param = param.concat(USER.getParam_key()).concat("=").concat(username);
        param = param.concat("&").concat(STATUS_.getParam_key()).concat("=").concat(STATUS_.getParam_value());
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Verify user get user status response body")
    public void verifyUserGetUserStatusResponseBody() {
        Response response = (Response) getJewelMapValue("response");
        ApiUtils.verifyResponseString("username", (String) getJewelMapValue("username")
                , getResponseMessageFor(response, "username"));
        verifyKeyPresent(response, "bridgeToken");
    }

    @And("Set endpoint {string} method {string} to get last {string} bridge token")
    public void setEndpointMethodToGetLastCountBridgeToken(String endpoint, String method, String count) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        param = param.concat(COUNT.getParam_key()).concat("=").concat(count);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }
}
