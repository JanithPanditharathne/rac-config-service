package com.zone24x7.rac.configservice.util;

public class CSResponse {

    private String status;
    private String code;
    private String message;

    public CSResponse() {
    }

    public CSResponse(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public CSResponse(String status, String messageWithCode) {
        this.status = status;

        String[] arr = messageWithCode.split(":");
        this.code = arr[0];
        this.message = arr[1].trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
