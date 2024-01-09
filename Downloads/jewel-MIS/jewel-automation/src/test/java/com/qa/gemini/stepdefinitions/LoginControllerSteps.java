package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.qa.gemini.payloads.LoginPayloads;
import com.qa.gemini.pojo.Authentication;
import com.qa.gemini.utils.ApiUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import static com.qa.gemini.utils.ApiUtils.verifyKeyPresent;
import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.GetFakeData.generatePassword;
import static com.qa.gemini.utils.GetFakeData.generateUsername;

public class LoginControllerSteps {

    @And("Set endpoint {string} method {string} and with user payload to login user")
    public void setEndpointMethodAndWithUserPayloadToLoginUser(String endpoint, String method) {
        final String username = (String) getJewelMapValue("username");
        final String password = decodeMD5((String) getJewelMapValue("password"));
        Authentication loginPayload = LoginPayloads.loginPayload(username, password);
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, loginPayload);
        setJewelMapValue("response", response);
    }

    @And("Verify user details in response body of login controller")
    public void verifyUserDetailsInResponseBodyOfLoginController() {
        Response response = (Response) getJewelMapValue("response");
        ApiUtils.verifyResponseString("username", (String) getJewelMapValue("username"),
                getResponseDataFor(response, "username"));
        ApiUtils.verifyResponseString("firstname", (String) getJewelMapValue("firstName"),
                getResponseDataFor(response, "firstName"));
        ApiUtils.verifyResponseString("lastname", (String) getJewelMapValue("lastName"),
                getResponseDataFor(response, "lastName"));
        ApiUtils.verifyResponseString("companyName", (String) getJewelMapValue("companyName"),
                getResponseDataFor(response, "company"));
    }

    @And("Set endpoint {string} method {string} and with user payload to login user without {string}")
    public void setEndpointMethodAndWithUserPayloadToLoginUserWithout(String endpoint, String method,
                                                                      String field) {
        final String username = (String) getJewelMapValue("username");
        final String password = decodeMD5((String) getJewelMapValue("password"));
        Authentication loginPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                loginPayload = LoginPayloads.loginPayload("", "");
                break;
            case "username":
                loginPayload = LoginPayloads.loginPayload("", password);
                break;
            case "password":
                loginPayload = LoginPayloads.loginPayload(username, "");
                break;
        }
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, loginPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user invalid payload to login user without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToLoginUserWithout(String endpoint, String method,
                                                                             String field) {
        final String username = (String) getJewelMapValue("username");
        final String password = decodeMD5((String) getJewelMapValue("password"));
        Authentication loginPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                loginPayload = LoginPayloads.loginPayloadWithoutAll();
                break;
            case "username":
                loginPayload = LoginPayloads.loginPayloadWithoutUsername(password);
                break;
            case "password":
                loginPayload = LoginPayloads.loginPayloadWithoutPassword(username);
                break;
        }
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, loginPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user incorrect {string} payload to login user")
    public void setEndpointMethodAndWithUserIncorrectPayloadToLoginUser(String endpoint, String method,
                                                                        String field) {
        String username = (String) getJewelMapValue("username");
        String password = decodeMD5((String) getJewelMapValue("password"));
        switch (field.toLowerCase()) {
            case "all":
                username = generateUsername();
                password = generatePassword();
                break;
            case "username":
                username = generateUsername();
                break;
            case "password":
                password = generatePassword();
                break;
        }
        Authentication loginPayload = LoginPayloads.loginPayload(username, password);
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, loginPayload);
        setJewelMapValue("response", response);
    }

    @And("Verify user login response body")
    public void verifyUserLoginResponseBody() {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyPresent(response, "role");
        verifyKeyPresent(response, "company");
        verifyKeyPresent(response, "socket");
        verifyKeyPresent(response, "username");
        verifyKeyPresent(response, "token");
    }
}
