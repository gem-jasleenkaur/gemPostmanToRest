package com.qa.gemini.utils;

public enum Params {
    USERNAME("username", ""),
    P_ID("pid", ""),
    REAL_COMPANY_NAME("realCompanyName", ""),
    COMPANY_NAME("companyName", ""),
    S_RUN_ID("s_run_id", ""),
    TAG("tag", ""),
    FOLDER("folder", ""),
    FILE("file", ""),
    FOLDER_NAME("folderName", ""),
    FILE_NAME("fileName", ""),
    ID("id", ""),
    DELETED("deleted", ""),
    USER("user", ""),
    STATUS_("status", "1"),
    COUNT("count", ""),
    TOKEN("token", ""),
    ACCESS_TYPE("accessType", "Public"),
    TYPE("type", "ADD, REMOVE"),
    MODE("mode", ""),
    PAGE_NO("pageNo", "");


    private final String param_key;
    private final String param_value;

    Params(String param_key, String param_value) {
        this.param_key = param_key;
        this.param_value = param_value;
    }

    public String getParam_key() {
        return param_key;
    }

    public String getParam_value() {
        return param_value;
    }
}
