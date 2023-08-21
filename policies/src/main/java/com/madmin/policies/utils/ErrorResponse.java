package com.madmin.policies.utils;

public class ErrorResponse {
    private String errorMessage;

    // Constructors, getters, and setters

    public ErrorResponse() {
    }

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getters and setters

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
