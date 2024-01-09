package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.utils.ProjectConfigData;
import com.qa.gemini.payloads.UserControllerPayloads;
import com.qa.gemini.pojo.Authentication;
import com.qa.gemini.pojo.Users;
import com.qa.gemini.utils.ApiUtils;
import com.qa.gemini.utils.DataFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qa.gemini.utils.ApiUtils.verifyKeyPresent;
import static com.qa.gemini.utils.CommonMethods.getJewelMapValue;
import static com.qa.gemini.utils.CommonMethods.setJewelMapValue;
import static com.qa.gemini.utils.GetFakeData.*;
import static com.qa.gemini.utils.Params.*;

public class UserControllerSteps {

    @When("Set endpoint {string} method {string} and with user payload to create user")
    public void setEndpointMethodAndWithUserPayloadToCreateUser(String endpoint, String method) {
        final String username = generateUsername();
        final String emailDomain = "gemperf.com";
        final String email = generateEmail(emailDomain);
        final String companyName = "GEMPERF PVT. LTD.";
        final String password = generatePassword();
        final String firstName = "firstName";
        final String lastName = "lastName";
        Authentication userPayload = UserControllerPayloads.createUserPayload(username, firstName,
                lastName, email, password, companyName);

        Response response = ApiUtils.hitApiWithPayload(endpoint, method, userPayload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 201) {
            setJewelMapValue("username", username);
            setJewelMapValue("email", email);
            setJewelMapValue("companyName", companyName);
            setJewelMapValue("password", password);
            setJewelMapValue("firstName", firstName);
            setJewelMapValue("lastName", lastName);
        }
    }

    @And("Set endpoint {string} method {string} and with user payload to delete user")
    public void setEndpointMethodAndWithUserPayloadToDeleteUser(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users userPayload = UserControllerPayloads.deleteUserPayload(usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to create user with existing {string}")
    public void setEndpointMethodAndWithUserPayloadToCreateUserWithExisting(String endpoint, String method,
                                                                            String field) {
        String username = null;
        String email = null;
        if (field.equalsIgnoreCase("username")) {
            username = (String) getJewelMapValue("username");
            final String emailDomain = "gemperf.com";
            email = generateEmail(emailDomain);
        } else if (field.equalsIgnoreCase("email")) {
            username = generateUsername();
            email = (String) getJewelMapValue("email");
        }
        final String companyName = "GEMPERF PVT. LTD.";
        Authentication userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                "lastname", email, generatePassword(), companyName);

        Response response = ApiUtils.hitApiWithPayload(endpoint, method, userPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to create user with unregistered company")
    public void setEndpointMethodAndWithUserPayloadToCreateUserWithUnregisteredCompany(String endpoint,
                                                                                       String method) {
        final String username = generateUsername();
        final String emailDomain = "undefined.com";
        final String email = generateEmail(emailDomain);
        final String companyName = "UNDEFINED PVT. LTD.";
        Authentication userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                "lastname", email, generatePassword(), companyName);

        Response response = ApiUtils.hitApiWithPayload(endpoint, method, userPayload);
        setJewelMapValue("response", response);
        if (response.getStatus() == 201) {
            setJewelMapValue("username", username);
            setJewelMapValue("email", email);
            setJewelMapValue("companyName", companyName);
        }
    }

    @When("Set endpoint {string} method {string} and with user payload to create user without {string}")
    public void setEndpointMethodAndWithUserPayloadToCreateUserWithout(String endpoint, String method,
                                                                       String field) {
        final String username = generateUsername();
        final String emailDomain = "gemperf.com";
        final String email = generateEmail(emailDomain);
        final String companyName = "GEMPERF PVT. LTD.";
        Authentication userPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                userPayload = UserControllerPayloads.createUserPayload("", "", "",
                        "", "", "");
                break;
            case "username":
                userPayload = UserControllerPayloads.createUserPayload("", "firstName",
                        "lastname", email, generatePassword(), companyName);
                break;
            case "firstname":
                userPayload = UserControllerPayloads.createUserPayload(username, "",
                        "lastname", email, generatePassword(), companyName);
                break;
            case "lastname":
                userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                        "", email, generatePassword(), companyName);
                break;
            case "email":
                userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                        "lastname", "", generatePassword(), companyName);
                break;
            case "password":
                userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                        "lastname", email, "", companyName);
                break;
            case "company":
                userPayload = UserControllerPayloads.createUserPayload(username, "firstName",
                        "lastname", email, generatePassword(), "");
                break;
        }
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, userPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user invalid payload to create user without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToCreateUserWithout(String endpoint, String method,
                                                                              String field) {
        final String username = generateUsername();
        final String emailDomain = "gemperf.com";
        final String email = generateEmail(emailDomain);
        final String companyName = "GEMPERF PVT. LTD.";
        Authentication userPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                userPayload = UserControllerPayloads.createUserPayloadWithoutValues();
                break;
            case "username":
                userPayload = UserControllerPayloads.createUserPayloadWithoutUsername("firstName",
                        "lastname", email, generatePassword(), companyName);
                break;
            case "firstname":
                userPayload = UserControllerPayloads.createUserPayloadWithoutFName(username,
                        "lastname", email, generatePassword(), companyName);
                break;
            case "lastname":
                userPayload = UserControllerPayloads.createUserPayloadWithoutLName(username, "firstName",
                        email, generatePassword(), companyName);
                break;
            case "email":
                userPayload = UserControllerPayloads.createUserPayloadWithoutEmail(username, "firstName",
                        "lastname", generatePassword(), companyName);
                break;
            case "password":
                userPayload = UserControllerPayloads.createUserPayloadWithoutPassword(username, "firstName",
                        "lastname", email, companyName);
                break;
            case "company":
                userPayload = UserControllerPayloads.createUserPayloadWithoutCompany(username, "firstName",
                        "lastname", email, generatePassword());
                break;
        }
        Response response = ApiUtils.hitApiWithPayload(endpoint, method, userPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user payload to delete user without {string}")
    public void setEndpointMethodAndWithUserPayloadToDeleteUserWithout(String endpoint, String method,
                                                                       String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add("");
        Users userPayload = UserControllerPayloads.deleteUserPayload(usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user invalid payload to delete user without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToDeleteUserWithout(String endpoint, String method,
                                                                              String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Users userPayload = UserControllerPayloads.deleteUserPayloadWithoutValue();
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to unblock user")
    public void setEndpointMethodAndWithUserPayloadToUnblockUser(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users userPayload = UserControllerPayloads.deleteUserPayload(usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user payload to unblock user without {string}")
    public void setEndpointMethodAndWithUserPayloadToUnblockUserWithout(String endpoint, String method,
                                                                        String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add("");
        Users userPayload = UserControllerPayloads.deleteUserPayload(usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} and with user invalid payload to unblock user without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToUnblockUserWithout(String endpoint, String method,
                                                                               String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Users userPayload = UserControllerPayloads.deleteUserPayloadWithoutValue();
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, userPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to add admin")
    public void setEndpointMethodAndWithUserPayloadToAddAdmin(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users adminPayload = UserControllerPayloads.addAdminPayload(
                (String) getJewelMapValue("companyName"), usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to remove admin")
    public void setEndpointMethodAndWithUserPayloadToRemoveAdmin(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users adminPayload = UserControllerPayloads.removeAdminPayload(
                (String) getJewelMapValue("companyName"), usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to add admin without {string}")
    public void setEndpointMethodAndWithUserPayloadToAddAdminWithout(String endpoint, String method,
                                                                     String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        Users adminPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                adminPayload = UserControllerPayloads.addAdminPayload("", usernames);
                break;
            case "username":
                adminPayload = UserControllerPayloads.addAdminPayload(
                        (String) getJewelMapValue("companyName"), usernames);
                break;
            case "realcompanyname":
                usernames.add((String) getJewelMapValue("username"));
                adminPayload = UserControllerPayloads.addAdminPayload("", usernames);
                break;
        }
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload to remove admin without {string}")
    public void setEndpointMethodAndWithUserPayloadToRemoveAdminWithout(String endpoint, String method,
                                                                        String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        Users adminPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                adminPayload = UserControllerPayloads.removeAdminPayload("", usernames);
                break;
            case "username":
                adminPayload = UserControllerPayloads.removeAdminPayload(
                        (String) getJewelMapValue("companyName"), usernames);
                break;
            case "realcompanyname":
                usernames.add((String) getJewelMapValue("username"));
                adminPayload = UserControllerPayloads.removeAdminPayload("", usernames);
                break;
        }
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user invalid payload to add admin without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToAddAdminWithout(String endpoint, String method,
                                                                            String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users adminPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                adminPayload = UserControllerPayloads.addAdminPayloadWithoutAll();
                break;
            case "username":
                adminPayload = UserControllerPayloads.addAdminPayloadWithoutUsername(
                        (String) getJewelMapValue("companyName"));
                break;
            case "realcompanyname":
                adminPayload = UserControllerPayloads.addAdminPayloadWithoutCompanyName(usernames);
                break;
        }
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user invalid payload to remove admin without {string}")
    public void setEndpointMethodAndWithUserInvalidPayloadToRemoveAdminWithout(String endpoint, String method,
                                                                               String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users adminPayload = null;
        switch (field.toLowerCase()) {
            case "all":
                adminPayload = UserControllerPayloads.removeAdminPayloadWithoutAll();
                break;
            case "username":
                adminPayload = UserControllerPayloads.removeAdminPayloadWithoutUsername(
                        (String) getJewelMapValue("companyName"));
                break;
            case "realcompanyname":
                adminPayload = UserControllerPayloads.removeAdminPayloadWithoutCompanyName(usernames);
                break;
        }
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with user payload having incorrect company name to add admin")
    public void setEndpointMethodAndWithUserPayloadHavingIncorrectCompanyNameToAddAdmin(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Users adminPayload = UserControllerPayloads.addAdminPayload("incorrect company pvt ltd",
                usernames);
        Response response = ApiUtils.hitApiWithTokenAndPayload(endpoint, method, header, adminPayload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to validate username")
    public void setEndpointMethodAndParamToValidateUsername(String endpoint, String method) {
        String param = "?";
        String username = getJewelMapValue("username") == null ? Integer.MAX_VALUE + "" :
                (String) getJewelMapValue("username");
        param = param.concat(USERNAME.getParam_key()).concat("=").concat(username);
        Response response = ApiUtils.hitApiWithParam(endpoint, method, param);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to validate invalid username")
    public void setEndpointMethodAndParamToValidateInvalidUsername(String endpoint, String method) {
        String param = "?";
        String username = "" + Integer.MAX_VALUE;
        param = param.concat(USERNAME.getParam_key()).concat("=").concat(username);
        Response response = ApiUtils.hitApiWithParam(endpoint, method, param);
        setJewelMapValue("response", response);
    }


    @And("Set endpoint {string} method {string} and param to get verified users for company {string}")
    public void setEndpointMethodAndParamToGetVerifiedUsersForCompany(String endpoint, String method,
                                                                      String company) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        try {
            company = URLEncoder.encode(company, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        param = param.concat(REAL_COMPANY_NAME.getParam_key()).concat("=").concat(company);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }


    @And("Set endpoint {string} method {string} and param to get same company users")
    public void setEndpointMethodAndParamToGetSameCompanyUsers(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithToken(endpoint, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to get same company users for project {int}")
    public void setEndpointMethodAndParamToGetSameCompanyUsersForProject(String endpoint, String method,
                                                                         int pid) {
        if (pid == -1) {
            pid = Integer.MAX_VALUE;
        }
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        param = param.concat(P_ID.getParam_key()).concat("=").concat(pid + "");
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to get company users for company {string}")
    public void setEndpointMethodAndParamToGetCompanyUsersForCompany(String endpoint, String method,
                                                                     String company) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        try {
            company = URLEncoder.encode(company, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        param = param.concat(COMPANY_NAME.getParam_key()).concat("=").concat(company);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to get company admins for company {string}")
    public void setEndpointMethodAndParamToGetCompanyAdminsForCompany(String endpoint, String method,
                                                                      String company) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        try {
            company = URLEncoder.encode(company, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        param = param.concat(REAL_COMPANY_NAME.getParam_key()).concat("=").concat(company);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and with username as param to add as unverified")
    public void setEndpointMethodAndWithUsernameAsParamToAddAsUnverified(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");

        String username = getJewelMapValue("username") == null ? Integer.MAX_VALUE + "" :
                (String) getJewelMapValue("username");
        String param = "?";
        param = param.concat(USERNAME.getParam_key()).concat("=").concat(username);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Verify user creation response body")
    public void verifyUserCreationResponseBody() {
        Response response = (Response) getJewelMapValue("response");
        verifyKeyPresent(response, "bridgeToken");
    }

    @And("Set endpoint {string} method {string} to get all users")
    public void setEndpointMethodToGetAllUsers(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        Response response = ApiUtils.hitApiWithToken(endpoint, method, header);
        setJewelMapValue("response", response);
    }

    @And("Verify response body for getting all users")
    public void verifyResponseBodyForGettingAllUsers() {
        try {
            Response response = (Response) getJewelMapValue("response");
            if (!response.getResponseBodyJson().getAsJsonArray().isEmpty()) {
                GemTestReporter.addTestStep("Response Body Verification", "All Users data is present.",
                        STATUS.PASS);
            } else {
                GemTestReporter.addTestStep("Response Body Verification", "All Users data is empty.",
                        STATUS.FAIL);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Response Body Error", "Something went wrong.",
                    STATUS.FAIL);
        }
    }

    @And("Set endpoint {string} method {string} and param to get username {string} details")
    public void setEndpointMethodAndParamToGetUsernameDetails(String endpoint, String method,
                                                              String username) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        if (username == null || username.isEmpty()) {
            username = ProjectConfigData.getProperty("jewel_user");
            username = username != null ? username :
                    DataFileReader.getInstance().getUsername((String) getJewelMapValue("usertype"));
        }
        param = param.concat(USERNAME.getParam_key()).concat("=").concat(username);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to get username deleted details")
    public void setEndpointMethodAndParamToGetUsernameDeletedDetails(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        String username = (String) getJewelMapValue("username");
        if (username == null || username.isEmpty()) {
            username = ProjectConfigData.getProperty("jewel_user");
            username = username != null ? username :
                    DataFileReader.getInstance().getUsername((String) getJewelMapValue("usertype"));
        }
        param = param.concat(USERNAME.getParam_key()).concat("=").concat(username);
        param = param.concat("&").concat(DELETED.getParam_key()).concat("=").concat("true");
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} and param to get company {string} details")
    public void setEndpointMethodAndParamToGetCompanyDetails(String endpoint, String method, String company) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("header");
        String param = "?";
        try {
            company = URLEncoder.encode(company, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        param = param.concat(REAL_COMPANY_NAME.getParam_key()).concat("=").concat(company);
        Response response = ApiUtils.hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }
}
