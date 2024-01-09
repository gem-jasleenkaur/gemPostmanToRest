package com.qa.gemini.stepdefinitions;

import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.qa.gemini.utils.ApiUtils;
import com.qa.gemini.utils.OperationStatus;
import com.qa.gemini.utils.ResponseMessages;
import io.cucumber.java.en.And;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static com.qa.gemini.utils.CommonMethods.*;

/**
 * This class is only for response body data (message and operation) verification.
 */
public class VerificationSteps {

    public static void verifyOperationMessageFor(String message) {
        Response response = (Response) getJewelMapValue("response");
        switch (message.toLowerCase()) {
            case "success":
                ApiUtils.verifyResponseString("operation", OperationStatus.SUCCESS.getStatus(),
                        getResponseMessageFor(response, "operation"));
                break;
            case "failure":
                ApiUtils.verifyResponseString("operation", OperationStatus.FAILURE.getStatus(),
                        getResponseMessageFor(response, "operation"));
                break;
            case "error":
                ApiUtils.verifyResponseString("operation", OperationStatus.ERROR.getStatus(),
                        getResponseMessageFor(response, "operation"));
                break;
            case "info":
                ApiUtils.verifyResponseString("operation", OperationStatus.INFO.getStatus(),
                        getResponseMessageFor(response, "operation"));
                break;
        }
    }

    @And("Verify response body message for {string} and operation message as {string}")
    public void verifyResponseBodyForOfLoginController(String step, String operation) {
        try {
            Response response = (Response) getJewelMapValue("response");
            switch (step.toLowerCase()) {
                // Login Controller verification
                case "login successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.LOGIN_SUCCESSFUL
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "login with incorrect all":
                case "login with incorrect username":
                case "login with empty all":
                case "login with empty username":
                case "unverified invalid user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_IS_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "login with incorrect password":
                case "login with empty password":
                    ApiUtils.verifyResponseString("message", ResponseMessages.PASSWORD_IS_INCORRECT
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;

                // User Controller Verification
                case "create user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_CREATED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "create user with existing username":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERNAME_ALREADY_EXISTS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "create user with existing email":
                    ApiUtils.verifyResponseString("message", ResponseMessages.EMAIL_ALREADY_EXISTS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "delete user with admin user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_DELETED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "delete user with empty field":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERNAME_IS_EMPTY_IN_THE_REQUEST
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "unblock user with admin user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERNAME_UNBLOCKED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "add admin with superadmin user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.ADMIN_ADDED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "remove admin with superadmin user":
                    ApiUtils.verifyResponseString("message", ResponseMessages.ADMIN_REMOVED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
//            case "add admin without all":
//            case "add admin without username":
//            case "remove admin without all":
//            case "remove admin without username":
//                ApiUtils.verifyResponseString("message", ResponseMessages.USER_DETAILS_ARE_NOT_FOUND
//                        .getMessage(), getResponseMessageFor(response, "message"));
//                break;
                case "validate username":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_EXISTS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "validate invalid username":
                    ApiUtils.verifyResponseString("message", ResponseMessages.INVALID_USERNAME
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "add to unverified":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_ADDED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "user exists":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_EXISIS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
//            case "unverified invalid user":
//                ApiUtils.verifyResponseString("message", ResponseMessages.USER_IS_NOT_FOUND
//                        .getMessage(), getResponseMessageFor(response, "message"));
//                break;

                // Pre Signed Controller Verification
                case "generate pre signed url successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.PRESIGNED_URL_CREATED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "generate pre signed url with empty filename":
                    ApiUtils.verifyResponseString("message", ResponseMessages.
                                    PRESIGNED_URL_NOT_GENERATED_PLEASE_ENTER_FILE.getMessage(),
                            getResponseMessageForErrors(response, ""));
                    break;

                // S3 Controller Verification
                case "upload successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILES_UPLOADED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "upload with no files":
                case "generate pre signed url with empty file":
                    ApiUtils.verifyResponseString("message", ResponseMessages.THERE_ARE_NO_FILES_IN_THE_BODY_OF_THE_REQUEST
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "generate pre signed url with empty s_run_id":
                case "generate pre signed url with invalid s_run_id":
                case "upload with invalid suite id":
                    ApiUtils.verifyResponseString("message", ResponseMessages.PROJECT_DETAILS_NOT_FOUND_2
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "upload with invalid tag":
                case "upload with invalid access type":
                case "generate pre signed url with empty tag":
                case "generate pre signed url with invalid tag":
                    ApiUtils.verifyResponseString("message", ResponseMessages.TAG_VALUE_IS_INCORRECT
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file not present":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILE_IS_NOT_PRESENT
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file delete successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILE_HAS_BEEN_DELETED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "data upload successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILES_UPLOADED
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "folder access provided":
                    ApiUtils.verifyResponseString("message", ResponseMessages.
                                    USERS_HAVE_NOW_WRITE_ACCESS_TO_THESE_FOLDERS.getMessage(),
                            getResponseMessageFor(response, "message"));
                    break;
                case "folder access revoked":
                    ApiUtils.verifyResponseString("message", ResponseMessages.
                                    USERS_NOW_DO_NOT_HAVE_WRITE_ACCESS_TO_THIS_FOLDERS.getMessage(),
                            getResponseMessageFor(response, "message"));
                    break;
                case "invalid folder access provided":
                case "invalid folder access revoked":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FOLDER_WITH_THIS_NAME_IS_NOT_FOUND
                            .getMessage(), getResponseMessageForErrors(response,
                            ((String) getJewelMapValue("folderName")).toUpperCase()));
                    break;
                case "folder access with invalid param":
                case "file access with invalid type param":
                    ApiUtils.verifyResponseString("message", ResponseMessages.TYPE_IS_NOT_CORRECT_PLEASE_WRITE_ADD_OR_REMOVE
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file read access provided":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_HAVE_NOW_READ_ACCESS_TO_THESE_FILES
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file read access revoked":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_NOW_DO_NOT_HAVE_READ_ACCESS_TO_THESE_FILES
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file write access provided":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_HAVE_NOW_WRITE_ACCESS_TO_THESE_FILES
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "file write access revoked":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USERS_NOW_DO_NOT_HAVE_WRITE_ACCESS_TO_THESE_FILES
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "invalid file access provided":
                case "invalid file access revoked":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILE_WITH_THIS_NAME_IS_NOT_FOUND
                            .getMessage(), getResponseMessageForErrors(response,
                            ((String) getJewelMapValue("fileName"))));
                    break;
                case "file access with invalid mode param":
                    ApiUtils.verifyResponseString("message", ResponseMessages.MODE_IS_NOT_CORRECT_PLEASE_WRITE_READ_OR_WRITE
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "get recycle bin files":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILES_IN_RECYCLE_BIN
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "delete recycle bin file":
                    ApiUtils.verifyResponseString("message", ResponseMessages.FILES_HAS_BEEN_DELETED_SUCCESSFULLY_FROM_RECYCLE_BIN
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;

                // Auth Token Controller
                case "change bridge token":
                    ApiUtils.verifyResponseString("message", ResponseMessages.TOKEN_CHANGED
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "get bridge token":
                    ApiUtils.verifyResponseString("message", ResponseMessages.TOKEN_FETCHED
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;

                // Variance
                case "variance added successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.VARIANCE_ADDED_SUCCESSFULLY
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "variance reset successful":
                    ApiUtils.verifyResponseString("message", ResponseMessages.VARIANCE_HAS_BEEN_UPDATED
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "variance found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.VARIANCE_DETAILS_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "variance not found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.VARIANCE_DETAILS_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;


                // Common Verification
                case "create user with empty field":
                case "add admin without all":
                case "add admin without username":
                case "remove admin without all":
                case "remove admin without username":
                case "add admin without realcompanyname":
                case "remove admin without realcompanyname":
                case "invalid payload":
                    ApiUtils.verifyResponseString("message", ResponseMessages.INVALID_DATA
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "accessing without admin":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_DOES_NOT_HAVE_ADMIN_ACCESS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "accessing without superadmin":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_DOES_NOT_HAVE_SUPER_ADMIN_ACCESS
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
//            case "accessing without superadmin 2":
//                ApiUtils.verifyResponseString("message", ResponseMessages.USER_DOES_NOT_HAVE_SUPER_ADMIN_ACCESS
//                        .getMessage(), getResponseMessageFor(response, "message"));
//                break;
                case "incorrect company":
//            case "add admin without realcompanyname":
//            case "remove admin without realcompanyname":
                    ApiUtils.verifyResponseString("message", ResponseMessages.COMPANY_DETAILS_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "incorrect company name":
                    ApiUtils.verifyResponseString("message", ResponseMessages.COMPANY_NAME_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "project not found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.PROJECT_DETAILS_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "no users found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.NO_USERS_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "user details not found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.USER_DETAILS_ARE_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "suite not found":
                    ApiUtils.verifyResponseString("message", ResponseMessages.SUITE_DETAILS_NOT_FOUND
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "upload without username":
                    ApiUtils.verifyResponseString("message", ResponseMessages.INVALID_BRIDGE_TOKEN_OR_USERNAME
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
                case "upload without all":
                case "upload without bridgetoken":
                    ApiUtils.verifyResponseString("message", ResponseMessages.TOKEN_IS_NOT_VALID
                            .getMessage(), getResponseMessageFor(response, "message"));
                    break;
            }
            verifyOperationMessageFor(operation);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Message Verification", "response is not valid.",
                    STATUS.FAIL);
        }
    }

    @And("Verify response body message for data {string} and operation message as {string}")
    public void verifyResponseBodyMessageForDataAndOperationMessageAs(String step, String operation) {
        Response response = (Response) getJewelMapValue("response");
        int dataLength = getResponseDataSize(response, "data");

        if (dataLength == 0) {
            ApiUtils.verifyResponseString("message", ResponseMessages.NO_RECORDS_FOUND
                    .getMessage(), getResponseMessageFor(response, "message"));
        } else {
            switch (step.toLowerCase()) {
                case "records found":
                    ApiUtils.verifyResponseString("message", dataLength +
                                    ResponseMessages.RECORDS_FOUND.getMessage(),
                            getResponseMessageFor(response, "message"));

                    break;
                case "users found":
                    ApiUtils.verifyResponseString("message", dataLength +
                                    ResponseMessages.USERS_ARE_FOUND.getMessage(),
                            getResponseMessageFor(response, "message"));
                    break;
                case "records are found":
                    ApiUtils.verifyResponseString("message", dataLength +
                                    ResponseMessages.RECORDS_ARE_FOUND.getMessage(),
                            getResponseMessageFor(response, "message"));
                    break;
            }
        }
        verifyOperationMessageFor(operation);
    }

    @And("Verify response body message for {string}")
    public void verifyResponseBodyMessageFor(String step) {
        Response response = (Response) getJewelMapValue("response");
        if (step.equalsIgnoreCase("upload file using expired pre-signed url")) {
            Document xmlBody = getXMLBody(response.getErrorMessage());
            Element rootElement = xmlBody.getDocumentElement();
            NodeList messageList = rootElement.getElementsByTagName("Message");
            if (messageList.getLength() > 0) {
                Node messageNode = messageList.item(0);
                String messageValue = messageNode.getTextContent();
                System.out.println("Message: " + messageValue);
                ApiUtils.verifyResponseString("Message", ResponseMessages.REQUEST_HAS_EXPIRED
                        .getMessage(), messageValue);
            } else {
                GemTestReporter.addTestStep("Response Body Error",
                        "Message element not found in the XML.", STATUS.FAIL);
            }
        }
    }

    @And("Verify response body message for rowdata {string} and operation message as {string}")
    public void verifyResponseBodyMessageForRowdataAndOperationMessageAs(String step, String operation) {
        Response response = (Response) getJewelMapValue("response");
        int dataLength = getResponseDataSize(response, "rowData");

        if (dataLength == 0) {
            ApiUtils.verifyResponseString("message", ResponseMessages.NO_RECORDS_FOUND
                    .getMessage(), getResponseMessageFor(response, "message"));
        } else {
            if (step.equalsIgnoreCase("get bridge token")) {
                ApiUtils.verifyResponseString("message", ResponseMessages.LAST_X_TOKENS_FETCHED
                                .getMessage().replace("X", dataLength + ""),
                        getResponseMessageFor(response, "message"));
            }
        }
        verifyOperationMessageFor(operation);
    }
}