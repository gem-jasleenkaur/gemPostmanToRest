package com.qa.gemini.utils;

public enum ResponseMessages {

    USER_CREATED_SUCCESSFULLY("User created successfully"),
    USER_DELETED_SUCCESSFULLY("Users deleted successfully"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USER_DOES_NOT_HAVE_ADMIN_ACCESS("User does not have admin access"),
    USER_DOEN_T_HAVE_SUPER_ADMIN_ACCESS("User doen't have super admin access"),
    USER_DOES_NOT_HAVE_SUPER_ADMIN_ACCESS("User does not have super admin access"),
    INVALID_DATA("Invalid Data"),
    COMPULSORY_FIELD_EXCEPTION("Compulsory Field Exception"),
    USERNAME_IS_EMPTY_IN_THE_REQUEST("Usernames is empty in the request"),
    USERNAME_UNBLOCKED_SUCCESSFULLY("Users unblocked successfully"),
    ADMIN_ADDED_SUCCESSFULLY("Admin added successfully"),
    ADMIN_REMOVED_SUCCESSFULLY("Admin removed Successfully"),
    COMPANY_DETAILS_NOT_FOUND("Company Details Not Found !!"),
    COMPANY_NAME_NOT_FOUND("Company name Not Found"),
    PROJECT_DETAILS_NOT_FOUND("Project Details not found"),
    PROJECT_DETAILS_NOT_FOUND_2("Project details not found !!"),
    USER_EXISTS("User Exists"),
    USER_DOES_NOT_EXISTS("User Does Not Exists"),
    INVALID_USERNAME("Invalid username"),
    NO_RECORDS_FOUND("No records found !!"),
    RECORDS_FOUND(" records found"),
    RECORDS_ARE_FOUND(" Records are found"),
    USERS_ARE_FOUND(" users are found"),
    LOGIN_SUCCESSFUL("Login successful"),
    USERNAME_IS_NOT_FOUND("Username is not found !!"),
    PASSWORD_IS_INCORRECT("Password is incorrect !!"),
    USERS_ADDED_SUCCESSFULLY("Users added successfully"),
    USER_IS_NOT_FOUND("User is not found!!"),
    USER_ADDED_SUCCESSFULLY("Users added successfully"),
    NO_USERS_FOUND("No users found"),
    USER_DETAILS_ARE_NOT_FOUND("User details are not found"),
    PRESIGNED_URL_CREATED_SUCCESSFULLY("Presigned Url Created SuccessFully"),
    FILES_UPLOADED_SUCCESSFULLY("Files Uploaded SuccessFully"),
    FILES_UPLOADED("File Uploaded"),
    SUITE_DETAILS_NOT_FOUND("Suite details not found !!"),
    TAG_VALUE_IS_INCORRECT("tag value is incorrect"),
    THERE_ARE_NO_FILES_IN_THE_BODY_OF_THE_REQUEST("There are no files in the body of the request"),
    INVALID_BRIDGE_TOKEN_OR_USERNAME("Invalid Bridge Token or username"),
    TOKEN_IS_NOT_VALID("Token is not valid"),
    FILE_IS_NOT_PRESENT("File is not present"),
    FILE_HAS_BEEN_DELETED_SUCCESSFULLY("files has been deleted successfully"),
    USERS_EXISIS("User Exists"),
    TOKEN_CHANGED("Token Changed"),
    TOKEN_FETCHED("Token Fetched"),
    LAST_X_TOKENS_FETCHED("Last X tokens(s) fetched"),
    USERS_HAVE_NOW_WRITE_ACCESS_TO_THESE_FOLDERS("Users have now write access to these folders"),
    USERS_NOW_DO_NOT_HAVE_WRITE_ACCESS_TO_THIS_FOLDERS("Users now do not have write access to this folders"),
    FOLDER_WITH_THIS_NAME_IS_NOT_FOUND("folder with this name is not found"),
    TYPE_IS_NOT_CORRECT_PLEASE_WRITE_ADD_OR_REMOVE("Type is not correct please write ADD or REMOVE"),
    USERS_HAVE_NOW_READ_ACCESS_TO_THESE_FILES("Users have now read access to these files"),
    USERS_NOW_DO_NOT_HAVE_READ_ACCESS_TO_THESE_FILES("Users now do not have read access to these files"),
    USERS_HAVE_NOW_WRITE_ACCESS_TO_THESE_FILES("Users now have write access to these files"),
    MODE_IS_NOT_CORRECT_PLEASE_WRITE_READ_OR_WRITE("mode is not correct please write READ or WRITE"),
    USERS_NOW_DO_NOT_HAVE_WRITE_ACCESS_TO_THESE_FILES("Users now do not have write access to these files"),
    FILE_WITH_THIS_NAME_IS_NOT_FOUND("File with this name not found"),
    FILES_IN_RECYCLE_BIN("Files in Recycle Bin"),
    FILES_HAS_BEEN_DELETED_SUCCESSFULLY_FROM_RECYCLE_BIN("files has been deleted successfully from Recycle Bin"),
    PRESIGNED_URL_NOT_GENERATED_PLEASE_ENTER_FILE("PreSigned Url not generated please enter file"),
    REQUEST_HAS_EXPIRED("Request has expired"),
    VARIANCE_ADDED_SUCCESSFULLY("Variance added successfully"),
    VARIANCE_DETAILS_FOUND("Variance details found !!"),
    VARIANCE_DETAILS_NOT_FOUND("Variance details not found !!"),
    VARIANCE_HAS_BEEN_UPDATED("Variance has been updated!! ");


    private final String message;

    ResponseMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
