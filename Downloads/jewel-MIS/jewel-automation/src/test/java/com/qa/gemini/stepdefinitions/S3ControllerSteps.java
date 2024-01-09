package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.qa.gemini.payloads.S3Payloads;
import com.qa.gemini.pojo.S3;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qa.gemini.payloads.S3Payloads.deletePayload;
import static com.qa.gemini.payloads.S3Payloads.recycleBinDelete;
import static com.qa.gemini.utils.ApiUtils.*;
import static com.qa.gemini.utils.CommonMethods.*;
import static com.qa.gemini.utils.GetFakeData.*;
import static com.qa.gemini.utils.Params.*;

public class S3ControllerSteps {
    @When("Set endpoint {string} method {string} param and {int} file to upload")
    public void setEndpointMethodParamAndNumFileToUpload(String endpoint, String method, int num) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String folderName = generateFolderName();
        String param = "?";
        param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateTempSRunId()).concat("&")
                .concat(TAG.getParam_key()).concat("=").concat("Protected").concat("&")
                .concat(FOLDER.getParam_key().concat("=")).concat(folderName);

        List<String> files = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            files.add(getFakeImage(createFile(generateFileName(), "png")));
        }
        Response response = hitApiWithParamTokenAndFiles(endpoint, param, method, header, files);
        setJewelMapValue("response", response);
        for (String file : files) {
            deleteFile(file);
        }
        if (response.getStatus() == 200) {
            List<String> fileIds = new ArrayList<>();
            JsonArray array = response.getResponseBodyJson().getAsJsonObject().get("data").getAsJsonArray();
            for (JsonElement element : array) {
                fileIds.add(element.getAsJsonObject().get("id").getAsString());
            }
            setJewelMapValue("folderName", folderName);
            setJewelMapValue("fileIds", fileIds);
            setJewelMapValue("files", files);
        }
    }

    @And("Verify the number of uploaded files as {int} in response")
    public void verifyTheNumberOfUploadedFilesAsNumInResponse(int num) {
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
    }

    @And("Set endpoint {string} method {string} and payload to delete uploaded files")
    public void setEndpointMethodAndPayloadToDeleteUploadedFiles(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<String> files = (List<String>) getJewelMapValue("files");
        String folderName = (String) getJewelMapValue("folderName");
        List<S3> payload = new ArrayList<>();
        for (String file : files) {
            payload.add(deletePayload(new File(file).getName(), folderName));
        }
        Response response = hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} invalid {string} param and {int} file to upload")
    public void setEndpointMethodInvalidSuiteIdParamAndNumFileToUpload(String endpoint, String method,
                                                                       String field, int num) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String folderName = generateFolderName();
        String param = "?";
        if (field.equalsIgnoreCase("suite id")) {
            param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateInvalidSRunId()).concat("&")
                    .concat(TAG.getParam_key()).concat("=").concat("Protected").concat("&")
                    .concat(FOLDER.getParam_key().concat("=")).concat(folderName);
        } else if (field.equalsIgnoreCase("tag")) {
            param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateTempSRunId()).concat("&")
                    .concat(TAG.getParam_key()).concat("=").concat("Invalid").concat("&")
                    .concat(FOLDER.getParam_key().concat("=")).concat(folderName);
        }

        List<String> files = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            files.add(createFile(generateFileName(), "png"));
        }
        Response response = hitApiWithParamTokenAndFiles(endpoint, param, method, header, files);
        setJewelMapValue("response", response);
        for (String file : files) {
            deleteFile(file);
        }
    }

    @When("Set endpoint {string} method {string} param and no file to upload")
    public void setEndpointMethodParamAndNoFileToUpload(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String folderName = generateFolderName();
        String param = "?";
        param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateTempSRunId()).concat("&")
                .concat(TAG.getParam_key()).concat("=").concat("Protected").concat("&")
                .concat(FOLDER.getParam_key().concat("=")).concat(folderName);

        Response response = hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @Then("Set endpoint {string} method {string} param to download file using method {int}")
    public void setEndpointMethodParamToDownloadFileUsingMethod(String endpoint, String method, int case_) {
        String param = "?";
        Map<String, String> header = null;
        List<String> ids = (List<String>) getJewelMapValue("fileIds");
        switch (case_) {
            case 1:
                header = (Map<String, String>) getJewelMapValue("bt_header");
                param = param.concat(ID.getParam_key()).concat("=").concat(ids.get(0));
                break;
            case 2:
                header = (Map<String, String>) getJewelMapValue("header");
                param = param.concat(ID.getParam_key()).concat("=").concat(ids.get(0));
                break;
            case 3:
                param = param.concat(ID.getParam_key()).concat("=").concat(ids.get(0))
                        .concat("&").concat(TOKEN.getParam_key()).concat("=")
                        .concat((String) getJewelMapValue("token"));
        }
        Response response = hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @Then("Set endpoint {string} method {string} invalid param to download file")
    public void setEndpointMethodInvalidParamToDownloadFile(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String invalid_id = "geco-cloud-serv:FOLDER_BEATAE_50:file_magni_9142936.png";
        String param = "?";
        param = param.concat(ID.getParam_key()).concat("=").concat(invalid_id);
        Response response = hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
    }

    @When("Set endpoint {string} method {string} param and to upload data")
    public void setEndpointMethodParamAndToUploadData(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String folderName = generateFolderName();
        String fileName = generateFileName() + ".txt";
        String param = "?";
        param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateTempSRunId()).concat("&")
                .concat(FOLDER.getParam_key().concat("=")).concat(folderName).concat("&")
                .concat(FILE.getParam_key().concat("=")).concat(fileName).concat("&")
                .concat(TAG.getParam_key()).concat("=").concat("Public");

        Object payload = new Faker().lorem().sentence();
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        List<String> files = new ArrayList<>();
        files.add(fileName);
        setJewelMapValue("response", response);
        setJewelMapValue("folderName", folderName);
        setJewelMapValue("files", files);
    }

    @When("Set endpoint {string} method {string} invalid {string} param and to upload data")
    public void setEndpointMethodInvalidParamAndToUploadData(String endpoint, String method, String field) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String folderName = generateFolderName();
        String fileName = generateFileName();
        String param = "?";
        if (field.equalsIgnoreCase("suite id")) {
            param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateInvalidSRunId()).concat("&")
                    .concat(FOLDER.getParam_key().concat("=")).concat(folderName).concat("&")
                    .concat(FILE.getParam_key().concat("=")).concat(fileName).concat("&")
                    .concat(TAG.getParam_key()).concat("=").concat("Public");
        } else if (field.equalsIgnoreCase("access type")) {
            param = param.concat(S_RUN_ID.getParam_key()).concat("=").concat(generateTempSRunId()).concat("&")
                    .concat(FOLDER.getParam_key().concat("=")).concat(folderName).concat("&")
                    .concat(FILE.getParam_key().concat("=")).concat(fileName).concat("&")
                    .concat(TAG.getParam_key()).concat("=").concat(new Faker().lorem().word());
        }
        Object payload = new Faker().lorem().sentence();
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} param and with access payload to {string} folder access")
    public void setEndpointMethodParamAndWithAccessPayloadToFolderAccess(String endpoint, String method,
                                                                         String type) {
        String folderName = (String) getJewelMapValue("folderName");
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.folderAccess(folderName, usernames);
        payload.add(s3Payload);
        String accessType = "";
        if (type.equalsIgnoreCase("provide")) {
            accessType = TYPE.getParam_value().split(",")[0].trim();
        } else if (type.equalsIgnoreCase("revoke")) {
            accessType = TYPE.getParam_value().split(",")[1].trim();
        }
        param = param.concat(TYPE.getParam_key()).concat("=").concat(accessType);
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} param and with non-existing folder access payload to {string} folder access")
    public void setEndpointMethodParamAndWithNonExistingFolderAccessPayloadToFolderAccess(String endpoint, String method,
                                                                                          String type) {
        String folderName = generateFolderName();
        setJewelMapValue("folderName", folderName);
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.folderAccess(folderName, usernames);
        payload.add(s3Payload);
        String accessType = "";
        if (type.equalsIgnoreCase("provide")) {
            accessType = TYPE.getParam_value().split(",")[0].trim();
        } else if (type.equalsIgnoreCase("revoke")) {
            accessType = TYPE.getParam_value().split(",")[1].trim();
        }
        param = param.concat(TYPE.getParam_key()).concat("=").concat(accessType);
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }


    @And("Set endpoint {string} method {string} invalid param and with access payload for folder access")
    public void setEndpointMethodInvalidParamAndWithAccessPayloadForFolderAccess(String endpoint,
                                                                                 String method) {
        String folderName = generateFolderName();
        setJewelMapValue("folderName", folderName);
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add(generateUsername());
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.folderAccess(folderName, usernames);
        payload.add(s3Payload);
        String accessType = "XYZ";
        param = param.concat(TYPE.getParam_key()).concat("=").concat(accessType);
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} param and with access payload to {string} file access in {string} mode")
    public void setEndpointMethodParamAndWithAccessPayloadToFileAccessInMode(String endpoint, String method,
                                                                             String type, String mode) {
        String folderName = (String) getJewelMapValue("folderName");
        String fileName = "";
        if (getJewelMapValue("files") != null) {
            fileName = ((List<String>) getJewelMapValue("files")).get(0);
            fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
        } else {
            fileName = generateFileName();
        }
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.fileAccess(fileName, folderName, usernames);
        payload.add(s3Payload);
        String accessType = "";
        if (type.equalsIgnoreCase("provide")) {
            accessType = TYPE.getParam_value().split(",")[0].trim();
        } else if (type.equalsIgnoreCase("revoke")) {
            accessType = TYPE.getParam_value().split(",")[1].trim();
        }
        param = param.concat(TYPE.getParam_key()).concat("=").concat(accessType).concat("&")
                .concat(MODE.getParam_key()).concat("=").concat(mode.toUpperCase());
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} param and with non-existing file payload to {string} file access in {string} mode")
    public void setEndpointMethodParamAndWithNonExistingFilePayloadToFileAccessInMode(String endpoint, String method,
                                                                                      String type, String mode) {
        String folderName = (String) getJewelMapValue("folderName");
        String fileName = generateFileName();
        setJewelMapValue("fileName", fileName);
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add((String) getJewelMapValue("username"));
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.fileAccess(fileName, folderName, usernames);
        payload.add(s3Payload);
        String accessType = "";
        if (type.equalsIgnoreCase("provide")) {
            accessType = TYPE.getParam_value().split(",")[0].trim();
        } else if (type.equalsIgnoreCase("revoke")) {
            accessType = TYPE.getParam_value().split(",")[1].trim();
        }
        param = param.concat(TYPE.getParam_key()).concat("=").concat(accessType).concat("&")
                .concat(MODE.getParam_key()).concat("=").concat(mode.toUpperCase());
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @And("Set endpoint {string} method {string} invalid {string} param and with access payload for file access")
    public void setEndpointMethodInvalidParamParamAndWithAccessPayloadForFileAccess(String endpoint,
                                                                                    String method,
                                                                                    String query) {
        String folderName = generateFolderName();
        setJewelMapValue("folderName", folderName);
        String fileName = generateFileName();
        setJewelMapValue("fileName", fileName);
        String param = "?";
        List<String> usernames = new ArrayList<>();
        usernames.add(generateUsername());
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        List<Object> payload = new ArrayList<>();
        S3 s3Payload = S3Payloads.folderAccess(folderName, usernames);
        payload.add(s3Payload);
        String type = "ADD";
        String mode = "READ";
        if (query.equalsIgnoreCase("type")) {
            type = "XYZ";
        } else if (query.equalsIgnoreCase("mode")) {
            mode = "ABC";
        }
        param = param.concat(TYPE.getParam_key()).concat("=").concat(type).concat("&")
                .concat(MODE.getParam_key()).concat("=").concat(mode);
        Response response = hitApiWithParamTokenAndPayload(endpoint, param, method, header, payload);
        setJewelMapValue("response", response);
    }

    @Then("Set endpoint {string} method {string} and param to get recycle bin files")
    public void setEndpointMethodAndParamToGetRecycleBinFiles(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        String param = "?";
        param = param.concat(PAGE_NO.getParam_key()).concat("=").concat("1");
        Response response = hitApiWithParamAndToken(endpoint, param, method, header);
        setJewelMapValue("response", response);
        setJewelMapValue("prevResponse", response);
    }

    @Then("Set endpoint {string} method {string} and payload to delete file from recycle bin")
    public void setEndpointMethodAndPayloadToDeleteFileFromRecycleBin(String endpoint, String method) {
        Map<String, String> header = (Map<String, String>) getJewelMapValue("bt_header");
        Response getResponse = (Response) getJewelMapValue("prevResponse");
        String fileId = getResponse.getResponseBodyJson().getAsJsonObject().get(
                "data").getAsJsonObject().get("data").getAsJsonArray().get(0).getAsString();
        GemTestReporter.addTestStep("File Id", "file to be deleted - " + fileId,
                STATUS.INFO);
        String fileName = fileId.split(":")[2];
        String folderName = fileId.split(":")[1];
        S3 s3Payload = recycleBinDelete(fileName, folderName);
        List<S3> payload = new ArrayList<>();
        payload.add(s3Payload);
        Response response = hitApiWithTokenAndPayload(endpoint, method, header, payload);
        setJewelMapValue("response", response);
    }
}
