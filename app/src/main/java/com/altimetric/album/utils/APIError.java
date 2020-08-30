package com.altimetric.album.utils;

public class APIError {

    private String errorCode;
    private String error;
    private String error_description;

    public APIError() {
    }

    public String errorCode() {
        return errorCode;
    }

    public String error() {
        return error;
    }

    public String error_description() {
        return error_description;
    }
}
