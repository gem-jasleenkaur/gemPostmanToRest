package com.qa.gemini.utils;

public enum OperationStatus {
    SUCCESS("Success"),
    FAILURE("Failure"),
    INFO("Info"),
    ERROR("Error");

    private final String status;

    OperationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
